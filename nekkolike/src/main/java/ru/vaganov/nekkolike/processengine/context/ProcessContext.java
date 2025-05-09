package ru.vaganov.nekkolike.processengine.context;

import ru.vaganov.nekkolike.processengine.instance.ProcessInstance;
import ru.vaganov.nekkolike.processengine.state.ProcessState;

import java.util.Map;

public interface ProcessContext {

    void runNextState(Long processId, ProcessState nestState, Map<String, Object> args);

    void waitNextState(Long processId, ProcessState nestState, Map<String, Object> args);

    void continueCurrentState(Long processId, Map<String, Object> args);

    ProcessInstance initProcess(Long newProcessId, ProcessState initState, Map<String, Object> args);

}
