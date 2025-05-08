package ru.vaganov.nekkolike.processengine.state;

import ru.vaganov.nekkolike.processengine.instance.ProcessInstance;

import java.util.Map;

public interface ProcessStep {

    NextStateRequest execute(ProcessInstance processInstance,
                             Map<String, Object> args);

    ProcessState getState();
}
