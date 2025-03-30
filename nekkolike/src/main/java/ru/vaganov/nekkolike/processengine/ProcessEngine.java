package ru.vaganov.nekkolike.processengine;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ProcessEngine {

    private Map<ProcessState, ProcessStep> steps;

    public void runCurrentState(ProcessInstance processInstance) {
        if (!steps.containsKey(processInstance.getCurrentState())) {
            throw new IllegalStateException("Нет такого процесса");
        }
        steps.get(processInstance.getCurrentState()).execute(processInstance, this);
    }

    public void waitInState(ProcessState newState, ProcessInstance processInstance) {
        processInstance.setCurrentState(newState);
    }

    public void runState(ProcessState newState, ProcessInstance processInstance) {
        processInstance.setCurrentState(newState);
        if (!steps.containsKey(processInstance.getCurrentState())) {
            throw new IllegalStateException("Нет такого процесса");
        }
        steps.get(processInstance.getCurrentState()).execute(processInstance, this);
    }

    public void registerStep(ProcessStep step) {
        if (steps == null) {
            steps = new HashMap<>();
        }
        steps.put(step.getState(), step);
    }

}
