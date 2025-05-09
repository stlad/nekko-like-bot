package ru.vaganov.nekkolike.processengine;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.processengine.instance.ProcessInstanceRepository;
import ru.vaganov.nekkolike.processengine.state.ProcessState;
import ru.vaganov.nekkolike.processengine.state.ProcessStep;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProcessEngine {

    private Map<ProcessState, ProcessStep> processSteps;
    private final ProcessInstanceRepository processInstanceRepository;


    public void registerStep(ProcessState state, ProcessStep step) {
        if (processSteps == null) {
            processSteps = new HashMap<>();
        }
        processSteps.put(state, step);
    }


    @Scheduled(fixedRate = 1000)
    public void run() {
        log.info("asdasdasd");
        var instanceOpt = processInstanceRepository.findAnyProcessToRun();
        if(instanceOpt.isEmpty()){
            log.debug("Не нашлось процессов, готовых к исполнению");
            return;
        }
        var instance = instanceOpt.get();
        log.info("Исполнение процесса {} для текущего состояния {}", instance.getId(), instance.getCurrentState());
        processSteps.get(instance.getCurrentState()).execute(instance);
    }
}
