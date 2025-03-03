package ru.vaganov.nekkolike.bot.exceptions;

public class CommandNotFoundException extends RuntimeException {

    private static final String BASE_MESSAGE = "Не найдена команда %s";

    public CommandNotFoundException(String message) {
        super(String.format(BASE_MESSAGE, message));
    }
}
