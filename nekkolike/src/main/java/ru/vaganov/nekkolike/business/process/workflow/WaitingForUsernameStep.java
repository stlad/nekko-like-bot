package ru.vaganov.nekkolike.business.process.workflow;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.business.process.NekkoProcessState;
import ru.vaganov.nekkolike.processengine.instance.ProcessInstance;
import ru.vaganov.nekkolike.processengine.state.NextStateRequest;
import ru.vaganov.nekkolike.processengine.state.ProcessState;

import java.util.Map;

@Component
@Slf4j
public class WaitingForUsernameStep implements NekkoProcessStep {

    public NextStateRequest execute(ProcessInstance processInstance,
                                    Map<String, Object> args) {
        log.info("Запущен шаг {} для чата {}", getState(), processInstance.getId());

        log.info("Привет, {}", args.get("message"));
        return new NextStateRequest(NekkoProcessState.START, true);
    }

    @Override
    public ProcessState getState() {
        return NekkoProcessState.WAIT_FOR_USERNAME;
    }
}
