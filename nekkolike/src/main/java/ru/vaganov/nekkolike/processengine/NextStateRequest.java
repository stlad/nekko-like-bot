package ru.vaganov.nekkolike.processengine;

public record NextStateRequest(ProcessState nextState, boolean waitForInput) {
}
