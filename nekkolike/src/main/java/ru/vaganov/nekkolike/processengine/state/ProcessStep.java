package ru.vaganov.nekkolike.processengine.state;

import ru.vaganov.nekkolike.processengine.instance.ProcessInstance;

public interface ProcessStep {

    void execute(ProcessInstance processInstance);

    ProcessState getState();
}
