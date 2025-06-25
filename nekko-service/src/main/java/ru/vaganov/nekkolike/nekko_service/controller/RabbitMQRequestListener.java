package ru.vaganov.nekkolike.nekko_service.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.common.dto.RabbitRequestDto;
import ru.vaganov.nekkolike.common.dto.RabbitResponseDto;
import ru.vaganov.nekkolike.nekko_service.business.cat.CatService;
import ru.vaganov.nekkolike.nekko_service.business.review.entity.ReviewRate;
import ru.vaganov.nekkolike.nekko_service.business.user.UserService;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMQRequestListener {

    private final ObjectMapper objectMapper;
    private final UserService userService;
    private final CatService catService;
    private final RabbitMQResponseSender responseSender;

    @RabbitListener(queues = "${spring.rabbitmq.queue_request.name}")
    public void onMessage(String message) {

        RabbitRequestDto dto = null;
        log.info("onMessage {}", message);
        try {
            dto = objectMapper.readValue(message, RabbitRequestDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }

        switch (dto.getAction()) {
            case REGISTER_USER_REQUEST -> {
                userService.registerUser(dto.getUserRegistrationDto());
            }
            case REGISTER_CAT_REQUEST -> {
                catService.createCat(dto.getCatRegistrationDto());
            }
            case GET_RANDOM_CAT_REQUEST -> {
                var response = catService.findRandomCat();
                responseSender.sendMessage(RabbitResponseDto.randomCat(dto.getChatId(), response));
            }
            case LIKE_CAT_REQUEST -> catService.rateCat(dto.getCatId(), dto.getChatId(), ReviewRate.LIKE);
            case DISLIKE_CAT_REQUEST -> catService.rateCat(dto.getCatId(), dto.getChatId(), ReviewRate.DISLIKE);
            default -> {
                log.error("Не удалось распознать команду");
                responseSender.sendMessage(RabbitResponseDto.error(dto.getChatId(), "Не удалось распознать команду"));
            }
        }

    }

}
