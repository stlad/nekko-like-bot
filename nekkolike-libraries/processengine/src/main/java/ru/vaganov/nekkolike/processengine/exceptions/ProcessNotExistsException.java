package ru.vaganov.nekkolike.processengine.exceptions;

public class ProcessNotExistsException extends RuntimeException {
    public ProcessNotExistsException(String message) {
        super(message);
    }
}
