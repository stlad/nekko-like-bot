package ru.vaganov.nekkolike.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserRegistrationDto {
    private final Long chatId;

    private final String telegramUsername;

    private String username;
}
