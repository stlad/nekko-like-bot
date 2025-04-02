package ru.vaganov.nekkolike.bot.process.workflow;

import lombok.extern.slf4j.Slf4j;
import ru.vaganov.nekkolike.bot.process.ProcessInstance;
import ru.vaganov.nekkolike.bot.process.ProcessState;

@Slf4j
public class UsernameReceivedState implements ProcessState {

    @Override
    public void usernameReceived(ProcessInstance instance, String username) {
        log.info("Получено имя пользователя {}", username);
        instance.setUsername(username);
        instance.setState(new MainMenuState());
        instance.getState().mainMenu(instance);
    }
}
