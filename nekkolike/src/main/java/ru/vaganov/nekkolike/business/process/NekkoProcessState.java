package ru.vaganov.nekkolike.business.process;

import ru.vaganov.nekkolike.processengine.ProcessState;

public enum NekkoProcessState implements ProcessState {
    START,
    WAIT_FOR_USERNAME
}
