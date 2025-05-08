package ru.vaganov.nekkolike.processengine.state;

import ru.vaganov.nekkolike.processengine.exceptions.ExecutionNotAllowedException;
import ru.vaganov.nekkolike.processengine.instance.ProcessInstance;
import ru.vaganov.nekkolike.processengine.io.OutputMessageProvider;

import java.util.Map;

public interface ProcessStep {

    NextStateRequest execute(ProcessInstance processInstance,
                                     OutputMessageProvider<?> out,
                                     Map<String, Object> args);

    ProcessState getState();
}
