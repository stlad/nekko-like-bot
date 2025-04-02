package ru.vaganov.nekkolike.process;

public interface ProcessState {

    void start(ProcessInstance instance);

    void waitForUsername(ProcessInstance instance, String username);

    void mainMenu(ProcessInstance instance);

}
