package ru.vaganov.nekkolike.business.process.workflow;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.bot.NekkoBot;
import ru.vaganov.nekkolike.business.process.NekkoProcessState;
import ru.vaganov.nekkolike.processengine.instance.ProcessInstance;
import ru.vaganov.nekkolike.processengine.state.ProcessState;

@Component
@Slf4j
@RequiredArgsConstructor
public class WaitingForUsernameStep implements NekkoProcessStep {
    private final NekkoBot bot;

    public void execute(ProcessInstance processInstance) {
        log.info("Запущен шаг {} для чата {}", getState(), processInstance.getId());


    }

    @Override
    public ProcessState getState() {
        return NekkoProcessState.WAIT_FOR_USERNAME;
    }
}
