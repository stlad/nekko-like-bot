package ru.vaganov.nekkolike.processengine;

import ru.vaganov.nekkolike.processengine.exceptions.ExecutionNotAllowedException;

import java.util.Map;

public interface ProcessStep {

    default NextStateRequest execute(ProcessInstance processInstance, Map<String, Object> args) {
        throw new ExecutionNotAllowedException("Данный метод требует входных параметров");
    }

    ProcessState getState();
}
