package ru.vaganov.nekkolike.processengine;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.processengine.exceptions.ProcessNotExistsException;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ProcessEngine {

    private Map<ProcessState, ProcessStep> processSteps;
    private final ProcessInstanceRepository processInstanceRepository;


    private ProcessInstance getProcessInstance(Long processId) {
        return processInstanceRepository.findById(processId)
                .orElseThrow(() -> new ProcessNotExistsException("Процесс с id: " + processId + " не существует"));
    }

    public void registerStep(ProcessState state, ProcessStep step) {
        if (processSteps == null) {
            processSteps = new HashMap<>();
        }
        processSteps.put(state, step);
    }

    public void continueProcess(Long processId) {

    }

    public void runCurrentState(Long processId, Map<String, Object> args) {
        var instance = getProcessInstance(processId);
        var result = processSteps.get(instance.getCurrentState()).execute(instance, args);
        if (result.waitForInput()) {
            waitInState(instance, result.nextState());
        } else {
            runCurrentState(processId, null);
        }
    }

    private void waitInState(ProcessInstance processInstance, ProcessState newState) {
        processInstance.waitIn(newState);
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
