package ru.vaganov.nekkolike.processengine.instance;

import lombok.Getter;
import ru.vaganov.nekkolike.processengine.state.ProcessExecutionState;
import ru.vaganov.nekkolike.processengine.state.ProcessState;

@Getter
public class ProcessInstance {

    private Long id;

    private ProcessState currentState;

    private ProcessExecutionState executionState;

    private String args;

    public void newState(ProcessState newState, String args) {
        currentState = newState;
        executionState = ProcessExecutionState.NEW;
        this.args = args;
    }

    public void waitIn(ProcessState newState) {
        currentState = newState;
        executionState = ProcessExecutionState.WAIT_INPUT;
    }

    public void complete(ProcessState newState) {
        currentState = newState;
        executionState = ProcessExecutionState.COMPLETED;
    }

    public ProcessInstance(Long id, ProcessState initState, String args) {
        this.id = id;
        newState(initState,args);
    }
}
