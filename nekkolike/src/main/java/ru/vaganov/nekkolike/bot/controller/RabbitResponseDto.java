package ru.vaganov.nekkolike.bot.controller;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//TODO данный класс должен лежать в пакете с backendClient
@Getter
@Setter
@Data
public class RabbitResponseDto {
    private final Long chatId;
    private final String telegramUsername;
    private final String errorText;

    //TODO тут нужные ДТОшки, из который берутся данные для процессов
}
