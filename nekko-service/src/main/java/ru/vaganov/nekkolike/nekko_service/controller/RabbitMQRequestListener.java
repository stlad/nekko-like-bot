package ru.vaganov.nekkolike.nekko_service.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.common.dto.RabbitRequestDto;
import ru.vaganov.nekkolike.nekko_service.business.cat.CatService;
import ru.vaganov.nekkolike.nekko_service.business.user.UserService;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMQRequestListener {

    private final ObjectMapper objectMapper;
    private final UserService userService;
    private final CatService catService;

    @RabbitListener(queues = "${spring.rabbitmq.queue_request.name}")
    public void onMessage(String message) {
        RabbitRequestDto dto = null;
        log.info("onMessage {}", message);
        try {
            dto = objectMapper.readValue(message, RabbitRequestDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }

        if(dto.getUserRegistrationDto() != null){
            userService.registerUser(dto.getUserRegistrationDto());
            return;
        }
        if(dto.getCatRegistrationDto() != null){
            catService.createCat(dto.getCatRegistrationDto());
        }
//        if(dto.getCatReview() != null && SHOW_CATS_REVIEW.equals(flow.getCurrentStep())){
//            flow.setCatReviewDto(dto.getCatReview());
//            workflowRepository.saveFlow(flow);
//            commandExecutor.executeCommand(WorkflowStep.SHOW_CAT_RECEIVED, updateData, telegramMessageSender);
//        }
//        else{
//            throw new CommandProcessingFailedException();
//        }
//        Для других команд.......
    }

}
