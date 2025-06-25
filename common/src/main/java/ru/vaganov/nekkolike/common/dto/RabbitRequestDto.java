package ru.vaganov.nekkolike.common.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//TODO данный класс должен лежать в пакете с backendClient
@Getter
@Setter
@Data
public class RabbitRequestDto {
    private Long chatId;
    private String telegramUsername;
    private String errorText;

    private CatRegistrationDto catRegistrationDto;
    private UserRegistrationDto userRegistrationDto;
    //TODO тут нужные ДТОшки, из который берутся данные для процессов
}
