package ru.vaganov.nekkolike.business.process.workflow.backend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.bot.controller.RabbitMQRequestSender;
import ru.vaganov.nekkolike.common.dto.CatListDto;
import ru.vaganov.nekkolike.common.dto.CatRegistrationDto;
import ru.vaganov.nekkolike.common.dto.RabbitRequestDto;
import ru.vaganov.nekkolike.common.dto.UserRegistrationDto;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class BackendClientImpl implements BackendClient {
    private final RabbitMQRequestSender requestSender;


    @Value("${nekko-service.cat-page-size}")
    private Integer pageSize;


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

    @Override
    public void getNextCatPage(Long chatId, CatListDto dto) {
        log.info("Получение следующей страницы с котиками {} {}", chatId, pageSize);
        var page = dto.getPage() == null
                ? 0
                : dto.getPage() + 1;
        requestSender.sendMessage(RabbitRequestDto.catPage(chatId, page, pageSize));
    }

    @Override
    public void getPrevCatPage(Long chatId, CatListDto dto) {
        log.info("Получение предыдущей страницы с котиками {} {}", chatId, pageSize);
        var page = Math.max((dto.getPage() - 1), 0);
        requestSender.sendMessage(RabbitRequestDto.catPage(chatId, page, pageSize));
    }

    @Override
    public void deleteCat(Long chatId, UUID catId) {
        log.info("Удаление котика");
        requestSender.sendMessage(RabbitRequestDto.deleteCat(chatId, catId));
    }

    @Override
    public void getCat(Long chatId, UUID catId) {
        log.info("Получение страницы котика");
        requestSender.sendMessage(RabbitRequestDto.concreteCat(chatId, catId));
    }
}
