package ru.vaganov.nekkolike.bot.process;

public interface ProcessState {

    default void start(ProcessInstance instance) {
        throw new IllegalStateException("Данное состояние недоступно");
    }

    default void waitForUsername(ProcessInstance instance) {
        throw new IllegalStateException("Данное состояние недоступно");
    }

    default void usernameReceived(ProcessInstance instance, String username) {
        throw new IllegalStateException("Данное состояние недоступно");
    }

    default void mainMenu(ProcessInstance instance) {
        throw new IllegalStateException("Данное состояние недоступно");
    }
}
