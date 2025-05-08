package ru.vaganov.nekkolike.business.process.workflow;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.business.process.NekkoProcessState;
import ru.vaganov.nekkolike.processengine.ProcessInstance;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class StartStep implements NekkoProcessStep<Object> {

    @Override
    public void execute(ProcessInstance processInstance, Map<String, Object> args) {
    }

    @Override
    public NekkoProcessState getState() {
        return NekkoProcessState.START;
    }
}
