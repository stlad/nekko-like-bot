package ru.vaganov.nekkolike.processengine.io;

public interface OutputMessageProvider<Out> {
    void send(Out message);
}
