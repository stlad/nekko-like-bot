package ru.vaganov.nekkolike.processengine;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class ProcessInstanceRepository {

    private Map<Long, ProcessInstance> processInstances = new HashMap<>();

    public Optional<ProcessInstance> findById(Long id) {
        return Optional.ofNullable(processInstances.getOrDefault(id, null));
    }

    public ProcessInstance save(ProcessInstance processInstance){
        processInstances.put(processInstance.getId(), processInstance);
        return processInstance;
    }
}
