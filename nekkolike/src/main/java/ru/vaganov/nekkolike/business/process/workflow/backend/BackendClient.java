package ru.vaganov.nekkolike.business.process.workflow.backend;


import ru.vaganov.nekkolike.common.dto.CatListDto;
import ru.vaganov.nekkolike.common.dto.CatRegistrationDto;
import ru.vaganov.nekkolike.common.dto.UserRegistrationDto;

import java.util.UUID;

public interface BackendClient {

    void requestRandomCat(Long chatId);

    void likeCat(Long chatId, UUID catId);

    void dislikeCat(Long chatId, UUID catId);

    void registerCat(Long chatId, CatRegistrationDto dto);

    void registerUser(Long chatId, UserRegistrationDto dto);

    void getNextCatPage(Long chatId, CatListDto dto);

    void getPrevCatPage(Long chatId, CatListDto dto);

    void deleteCat(Long chatId, UUID catId);

    void getCat(Long chatId, UUID catId);
}
