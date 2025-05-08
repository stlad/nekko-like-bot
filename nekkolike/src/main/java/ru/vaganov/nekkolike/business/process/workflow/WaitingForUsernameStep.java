package ru.vaganov.nekkolike.business.process.workflow;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.business.process.NekkoProcessState;
import ru.vaganov.nekkolike.processengine.ProcessState;

@Component
@Slf4j
public class WaitingForUsernameStep implements NekkoProcessStep<String> {

    @Override
    public void executeWithInput(Long instanceId, String input) {

    }

    @Override
    public ProcessState getState() {
        return NekkoProcessState.WAIT_FOR_USERNAME;
    }

    @Override
    public Class<String> getInputType() {
        return String.class;
    }
}
