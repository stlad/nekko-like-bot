package ru.vaganov.nekkolike.bot.process.workflow;

import lombok.extern.slf4j.Slf4j;
import ru.vaganov.nekkolike.bot.process.ProcessInstance;
import ru.vaganov.nekkolike.bot.process.ProcessState;

@Slf4j
public class WaitForUsernameState implements ProcessState {

    public void waitForUsername(ProcessInstance instance) {
        log.info("Процесс перешел в состояние \"Ожидание имени пользователя\" {}", instance.getId());
    }

}
