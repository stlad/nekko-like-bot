package ru.vaganov.nekkolike.bot.workflow;

import lombok.extern.slf4j.Slf4j;
import ru.vaganov.nekkolike.process.ProcessInstance;
import ru.vaganov.nekkolike.process.ProcessState;

@Slf4j
public class WaitForUsernameState implements ProcessState {
    @Override
    public void start(ProcessInstance instance) {
        throw new IllegalStateException("Данное состояние недоступно");
    }

    public void waitForUsername(ProcessInstance instance, String username) {
        log.info("Пользователь с именем {}", username);
        instance.setUsername(username);

    }

    @Override
    public void mainMenu(ProcessInstance instance) {

    }
}
