package ru.vaganov.nekkolike.bot.exceptions;

public class CommandProcessingFailedException extends RuntimeException {

    private static final String BASE_MESSAGE = "Не удалось обработать команду";

    public CommandProcessingFailedException() {
        super(BASE_MESSAGE);
    }
}
