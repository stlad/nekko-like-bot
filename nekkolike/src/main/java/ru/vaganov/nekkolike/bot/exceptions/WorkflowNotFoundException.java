package ru.vaganov.nekkolike.bot.exceptions;

public class WorkflowNotFoundException extends RuntimeException {

    private static final String BASE_MESSAGE = "Не найден процесс для chatId %s";

    public WorkflowNotFoundException(Long chatId) {
        super(String.format(BASE_MESSAGE, chatId));
    }
}
