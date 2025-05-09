package ru.vaganov.nekkolike.business.process;

import ru.vaganov.nekkolike.processengine.state.ProcessState;

public enum NekkoProcessState implements ProcessState {
    START,
    WAIT_FOR_USERNAME,
    MAIN_MENU,
    COMPLETE
}
