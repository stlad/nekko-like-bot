package ru.vaganov.nekkolike.bot.process.workflow;

import lombok.extern.slf4j.Slf4j;
import ru.vaganov.nekkolike.bot.process.ProcessInstance;
import ru.vaganov.nekkolike.bot.process.ProcessState;

@Slf4j
public class MainMenuState implements ProcessState {
    @Override
    public void mainMenu(ProcessInstance instance) {
      log.info("Главное меню для чата {}", instance.getId());
    }
}
