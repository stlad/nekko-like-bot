package ru.vaganov.nekkolike.bot.workflow;

import lombok.extern.slf4j.Slf4j;
import ru.vaganov.nekkolike.process.ProcessInstance;
import ru.vaganov.nekkolike.process.ProcessState;

@Slf4j
public class StartState implements ProcessState {

    @Override
    public void start(ProcessInstance instance) {
        log.info("Запущен процесс");
        instance.setState(new WaitForUsernameState());
    }

    @Override
    public void waitForUsername(ProcessInstance instance, String username) {
        throw new IllegalStateException("Данное состояние недоступно");
    }

    @Override
    public void mainMenu(ProcessInstance instance) {

    }
}
