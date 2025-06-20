package ru.vaganov.nekkolike.nekko_service.exception;

import java.io.IOException;

public class UserExistsException extends RuntimeException {
    private static final String BASE_MESSAGE = "Пользователь с ИД чата %s уже существует";

    public UserExistsException(Long chatId, Throwable cause) {
        super(String.format(BASE_MESSAGE, chatId), cause);
    }
}
