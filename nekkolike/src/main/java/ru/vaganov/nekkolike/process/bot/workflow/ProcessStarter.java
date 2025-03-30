package ru.vaganov.nekkolike.process.bot.workflow;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.processengine.ProcessEngine;
import ru.vaganov.nekkolike.processengine.ProcessInstance;

@Component
@RequiredArgsConstructor
public class ProcessStarter {
    private final ProcessEngine processEngine;

    public void startProcess(String user) {
        var processInstance = new ProcessInstance(user, NekkoBotProcessState.START);
        processEngine.runCurrentState(processInstance);
    }
}
