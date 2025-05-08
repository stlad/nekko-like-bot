package ru.vaganov.nekkolike.processengine;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.processengine.exceptions.ProcessNotExistsException;
import ru.vaganov.nekkolike.processengine.instance.ProcessInstance;
import ru.vaganov.nekkolike.processengine.instance.ProcessInstanceRepository;
import ru.vaganov.nekkolike.processengine.io.OutputMessageProvider;
import ru.vaganov.nekkolike.processengine.state.ProcessState;
import ru.vaganov.nekkolike.processengine.state.ProcessStep;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProcessEngine {

    private Map<ProcessState, ProcessStep> processSteps;
    private final ProcessInstanceRepository processInstanceRepository;

    public Optional<ProcessInstance> getProcessInstance(Long processId) {
        return processInstanceRepository.findById(processId);
    }

    public void registerStep(ProcessState state, ProcessStep step) {
        if (processSteps == null) {
            processSteps = new HashMap<>();
        }
        processSteps.put(state, step);
    }

    public void runCurrentState(Long processId, OutputMessageProvider out, Map<String, Object> args) {
        var instance = getProcessInstance(processId).orElseThrow(() -> new ProcessNotExistsException("Нет процесса с id: " + processId));
        log.info("Исполнение процесса {} для текущего состояния {}", processId, instance.getCurrentState());
        var result = processSteps.get(instance.getCurrentState()).execute(instance, out, args);
        if (result.waitForInput()) {
            waitInState(instance, result.nextState());
        } else {
            runCurrentState(processId, out, null);
        }
    }

    private void waitInState(ProcessInstance processInstance, ProcessState newState) {
        processInstance.waitIn(newState);
        log.info("Процесс {} ожидает ввода в состоянии {}", processInstance.getId(), processInstance.getCurrentState());
    }

    public ProcessInstance initProcess(Long newProcessId, ProcessState initState) {
        var processInstanceOpt = processInstanceRepository.findById(newProcessId);

        if (processInstanceOpt.isPresent()) {
            processInstanceOpt.get().newState(initState);
            return processInstanceOpt.get();
        }
        var processInstance = new ProcessInstance(newProcessId, initState);
        processInstanceRepository.save(processInstance);
        log.info("Создан процесс с id: {} в начальном состоянии: {}", newProcessId, initState);
        return processInstance;
    }
}
