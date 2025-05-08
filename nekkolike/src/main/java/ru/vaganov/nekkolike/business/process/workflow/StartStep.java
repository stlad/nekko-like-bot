package ru.vaganov.nekkolike.business.process.workflow;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.business.process.NekkoProcessState;
import ru.vaganov.nekkolike.processengine.instance.ProcessInstance;
import ru.vaganov.nekkolike.processengine.io.OutputMessageProvider;
import ru.vaganov.nekkolike.processengine.state.NextStateRequest;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class StartStep implements NekkoProcessStep {

    @Override
    public NextStateRequest execute(ProcessInstance processInstance,
                                    OutputMessageProvider<?> out,
                                    Map<String, Object> args) {
        log.info("Запущен процесс для чата {}", processInstance.getId());
        return new NextStateRequest(NekkoProcessState.WAIT_FOR_USERNAME, true);
    }

    @Override
    public NekkoProcessState getState() {
        return NekkoProcessState.START;
    }
}
