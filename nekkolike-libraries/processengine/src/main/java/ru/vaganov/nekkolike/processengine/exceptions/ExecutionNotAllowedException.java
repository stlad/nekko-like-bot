package ru.vaganov.nekkolike.processengine.exceptions;

public class ExecutionNotAllowedException extends RuntimeException {
    public ExecutionNotAllowedException(String message) {
        super(message);
    }
}
