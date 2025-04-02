package ru.vaganov.nekkolike.processengine.state;

public record NextStateRequest(ProcessState nextState, boolean waitForInput) {
}
