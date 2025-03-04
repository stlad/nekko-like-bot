package ru.vaganov.nekkolike.bot.exceptions;

public class FileProcessingException extends RuntimeException {

    public FileProcessingException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public FileProcessingException(String message) {
        super(message);
    }
}
