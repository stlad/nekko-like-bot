package ru.vaganov.nekkolike.processengine.instance;

import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.processengine.state.ProcessExecutionState;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class InMemoryProcessInstanceRepository implements ProcessInstanceRepository {

    private Map<Long, ProcessInstance> processInstances = new HashMap<>();

    public Optional<ProcessInstance> findById(Long id) {
        return Optional.ofNullable(processInstances.getOrDefault(id, null));
    }

    public ProcessInstance save(ProcessInstance processInstance) {
        processInstances.put(processInstance.getId(), processInstance);
        return processInstance;
    }

    @Override
    public Optional<ProcessInstance> findAnyProcessToRun() {
        return processInstances.values().stream()
                .filter(k -> k.getExecutionState().equals(ProcessExecutionState.NEW))
                .findAny();
    }
}
