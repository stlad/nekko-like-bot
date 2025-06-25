package ru.vaganov.nekkolike.nekko_service.business.user.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class UserRegistrationDto {

    @NotNull(message = "ИД чата не может быть пустым")
    private Long chatId;

    @NotNull(message = "Никнейма в телеграм не может быть пустым")
    private String telegramUsername;

    @NotNull(message = "Имя пользователя не может быть пустым")
    private String username;
}
