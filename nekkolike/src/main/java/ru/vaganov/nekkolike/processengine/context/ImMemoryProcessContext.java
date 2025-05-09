package ru.vaganov.nekkolike.processengine.context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.processengine.exceptions.ProcessNotExistsException;
import ru.vaganov.nekkolike.processengine.instance.ProcessInstance;
import ru.vaganov.nekkolike.processengine.instance.ProcessInstanceRepository;
import ru.vaganov.nekkolike.processengine.state.ProcessState;

import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ImMemoryProcessContext implements ProcessContext {

    private final ProcessInstanceRepository processInstanceRepository;
    private final ObjectMapper objectMapper;

    private Optional<ProcessInstance> getProcessInstance(Long processId) {
        return processInstanceRepository.findById(processId);
    }

    @Override
    public void runNextState(Long processId, ProcessState nextState, Map<String, Object> args) {
        var instance = getProcessInstance(processId);
        if (instance.isEmpty()) {
            log.info("Процесса с id: {} не существует", nextState);
            initProcess(processId, nextState, args);
            return;
        }
        try {

            instance.get().newState(nextState, objectMapper.writeValueAsString(args));
        } catch (JsonProcessingException exception) {
            log.error("Не удлалось обработать аргументы для процесса");
        }
    }

    @Override
    public void waitNextState(Long processId, ProcessState nextState, Map<String, Object> args) {
        var instance = getProcessInstance(processId)
                .orElseThrow(() -> new ProcessNotExistsException("Нет процесса с id: " + processId));
        instance.waitIn(nextState);
        processInstanceRepository.save(instance);
    }

    @Override
    public void continueCurrentState(Long processId, Map<String, Object> args) {

    }

    @Override
    public ProcessInstance initProcess(Long newProcessId, ProcessState initState, Map<String, Object> args) {
        var processInstanceOpt = processInstanceRepository.findById(newProcessId);
        String strArgs;
        try {
            strArgs = objectMapper.writeValueAsString(args);
        } catch (JsonProcessingException e) {
            log.error("Не удлалось обработать аргументы для процесса");
            throw new RuntimeException(e);
        }
        if (processInstanceOpt.isPresent()) {
            processInstanceOpt.get().newState(initState, strArgs);
            return processInstanceOpt.get();
        }
        var processInstance = new ProcessInstance(newProcessId, initState, strArgs);
        processInstanceRepository.save(processInstance);
        log.info("Создан процесс с id: {} в начальном состоянии: {}", newProcessId, initState);
        return processInstance;
    }
}
