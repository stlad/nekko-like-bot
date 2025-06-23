package ru.vaganov.nekkolike.business.process.workflow.backend;

import ru.vaganov.nekkolike.business.process.workflow.dto.CatRegistrationDto;
import ru.vaganov.nekkolike.business.process.workflow.dto.UserRegistrationDto;

import java.util.UUID;

public interface BackendClient {

    void requestRandomCat(Long chatId);

    void likeCat(Long chatId, UUID catId);

    void dislikeCat(Long chatId, UUID catId);

    void registerCat(Long chatId, CatRegistrationDto dto);

    void registerUser(Long chatId, UserRegistrationDto dto);

    void getCatPage(Long chatId, Integer page, Integer size);

    void deleteCat(Long chatId, UUID catId);

    void getCat(Long chatId, UUID catId);
}
