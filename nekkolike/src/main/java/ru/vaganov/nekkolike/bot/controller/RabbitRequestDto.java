package ru.vaganov.nekkolike.bot.controller;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.vaganov.nekkolike.business.process.workflow.dto.CatRegistrationDto;
import ru.vaganov.nekkolike.business.process.workflow.dto.UserRegistrationDto;

//TODO данный класс должен лежать в пакете с backendClient
@Getter
@Setter
@Data
public class RabbitRequestDto {
    private final Long chatId;
    private final String telegramUsername;
    private final String errorText;

    private CatRegistrationDto catRegistrationDto;
    private UserRegistrationDto userRegistrationDto;
    //TODO тут нужные ДТОшки, из который берутся данные для процессов
}
