package ru.vaganov.nekkolike.process.bot.workflow.step;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.process.bot.workflow.NekkoBotProcessState;
import ru.vaganov.nekkolike.processengine.ProcessEngine;
import ru.vaganov.nekkolike.processengine.ProcessInstance;
import ru.vaganov.nekkolike.processengine.ProcessState;
import ru.vaganov.nekkolike.processengine.ProcessStep;

@Component
@RequiredArgsConstructor
@Slf4j
public class StartStep implements ProcessStep {

    @Override
    public void execute(ProcessInstance processInstance, ProcessEngine processEngine) {
        log.info("Начат процесс исполнения этапа {}", processInstance.getCurrentState());
        processEngine.runState(NekkoBotProcessState.ASK_NAME, processInstance);
    }

    @Override
    public ProcessState getState() {
        return NekkoBotProcessState.START;
    }
}
