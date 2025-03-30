package ru.vaganov.nekkolike.processengine;

public interface ProcessStep {

    void execute(ProcessInstance processInstance, ProcessEngine engine);

    ProcessState getState();
}
