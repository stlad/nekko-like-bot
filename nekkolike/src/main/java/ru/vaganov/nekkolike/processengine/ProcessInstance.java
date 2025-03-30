package ru.vaganov.nekkolike.processengine;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProcessInstance {

    private String user;
    private ProcessState currentState;


}
