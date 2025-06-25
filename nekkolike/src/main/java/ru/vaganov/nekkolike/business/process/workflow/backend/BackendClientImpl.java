package ru.vaganov.nekkolike.business.process.workflow.backend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.bot.controller.RabbitMQRequestSender;
import ru.vaganov.nekkolike.common.dto.CatRegistrationDto;
import ru.vaganov.nekkolike.common.dto.RabbitRequestDto;
import ru.vaganov.nekkolike.common.dto.UserRegistrationDto;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class BackendClientImpl implements BackendClient {
    private final RabbitMQRequestSender requestSender;


    @Override
    public void requestRandomCat(Long chatId) {
        log.info("Отправлен запрос на котика на бэкенд");
        requestSender.sendMessage(RabbitRequestDto.randomCat(chatId));
    }

    @Override
    public void likeCat(Long chatId, UUID catId) {
        log.info("Отправлена команда лайк котика на бэкенд");
        requestSender.sendMessage(RabbitRequestDto.likeCat(chatId, catId));
    }

    @Override
    public void dislikeCat(Long chatId, UUID catId) {
        log.info("Отправлена команда дизлайк котика на бэкенд");
        requestSender.sendMessage(RabbitRequestDto.dislikeCat(chatId, catId));
    }

    @Override
    public void registerCat(Long chatId, CatRegistrationDto dto) {
        log.info("Отправлена команда регистрации котика на бэкенд");
        requestSender.sendMessage(RabbitRequestDto.registerCat(chatId, dto));

    }

    @Override
    public void registerUser(Long chatId, UserRegistrationDto dto) {
        log.info("Отправлена команда регистрации пользователя на бэкенд");
        requestSender.sendMessage(RabbitRequestDto.registerUser(chatId, dto));
    }
}
