package ru.vaganov.nekkolike.business.process.workflow.backend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.business.process.workflow.dto.CatRegistrationDto;
import ru.vaganov.nekkolike.business.process.workflow.dto.UserRegistrationDto;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class BackendClientImpl implements BackendClient {
    @Override
    public void requestRandomCat(Long chatId) {
        log.info("Отправлен запрос на котика на бэкенд");
    }

    @Override
    public void likeCat(Long chatId, UUID catId) {
        log.info("Отправлена команда лайк котика на бэкенд");
    }

    @Override
    public void dislikeCat(Long chatId, UUID catId) {
        log.info("Отправлена команда дизлайк котика на бэкенд");

    }

    @Override
    public void registerCat(Long chatId, CatRegistrationDto dto) {
        log.info("Отправлена команда регистрации котика на бэкенд");

    }

    @Override
    public void registerUser(Long chatId, UserRegistrationDto dto) {
        log.info("Отправлена команда регистрации пользователя на бэкенд");
    }

    @Override
    public void getCatPage(Long chatId, Integer page, Integer size) {
        log.info("Получение страницы с котиками {} {} {}", chatId, page, size);

    }

    @Override
    public void deleteCat(Long chatId, UUID catId) {

        log.info("Удаление котика");
    }

    @Override
    public void getCat(Long chatId, UUID catId) {
        log.info("Получение страницы котика");
    }
}
