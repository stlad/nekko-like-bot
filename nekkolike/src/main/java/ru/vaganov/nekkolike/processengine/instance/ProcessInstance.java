package ru.vaganov.nekkolike.processengine.instance;

import lombok.*;
import ru.vaganov.nekkolike.processengine.state.ProcessExecutionState;
import ru.vaganov.nekkolike.processengine.state.ProcessState;

@Getter
public class ProcessInstance {

    private Long id;

    private ProcessState currentState;

    private ProcessExecutionState executionState;

    public void newState(ProcessState newState) {
        currentState = newState;
        executionState = ProcessExecutionState.NEW;
    }

    public void waitIn(ProcessState newState){
        currentState = newState;
        executionState = ProcessExecutionState.WAIT_INPUT;
    }

    public ProcessInstance(Long id, ProcessState initState){
        this.id = id;
        newState(initState);
    }
}
