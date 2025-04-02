package ru.vaganov.nekkolike.bot.process.workflow;

import lombok.extern.slf4j.Slf4j;
import ru.vaganov.nekkolike.bot.process.ProcessInstance;
import ru.vaganov.nekkolike.bot.process.ProcessState;

@Slf4j
public class StartState implements ProcessState {

    @Override
    public void start(ProcessInstance instance) {
        log.info("Запущен процесс для чата {}", instance.getId());
        instance.setState(new WaitForUsernameState());
        instance.getState().waitForUsername(instance);
    }
}
