package ru.vaganov.nekkolike.processengine;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ProcessEngine {

    private Map<ProcessState, ProcessStep> processSteps;
    private final ProcessInstanceRepository processInstanceRepository;


    private ProcessInstance getProcessInstance(Long processId) {
        return processInstanceRepository.findById(processId).orElseThrow();
    }

    public void registerStep(ProcessState state, ProcessStep step) {
        if (processSteps == null) {
            processSteps = new HashMap<>();
        }
        processSteps.put(state, step);
    }

    public void runCurrentState(Long processId, Map<String, Object> args) {
        var instance = getProcessInstance(processId);
        processSteps.get(instance.getCurrentState()).execute(instance, args);
    }

    public void setAndRunState(Long processId, ProcessState initState, Map<String, Object> args) {
        var instance = getProcessInstance(processId);
        instance.newState(initState);
        processSteps.get(instance.getCurrentState()).execute(instance, args);
    }

    public ProcessInstance initProcess(Long newProcessId, ProcessState initState) {
        var processInstanceOpt = processInstanceRepository.findById(newProcessId);

        if (processInstanceOpt.isPresent()) {
            processInstanceOpt.get().newState(initState);
            return processInstanceOpt.get();
        }
        var processInstance = new ProcessInstance(newProcessId, initState);
        processInstanceRepository.save(processInstance);
        return processInstance;
    }
}
