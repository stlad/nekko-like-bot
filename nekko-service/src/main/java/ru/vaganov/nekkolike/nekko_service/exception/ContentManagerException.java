package ru.vaganov.nekkolike.nekko_service.exception;

import java.io.IOException;

public class ContentManagerException extends RuntimeException {
    private static final String BASE_MESSAGE = "Не удалось обработать файл %s";

    public ContentManagerException(String message, IOException cause) {
        super(String.format(BASE_MESSAGE, message), cause);
    }
}
