package ru.vaganov.nekkolike.process;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProcessInstance {
    private Long id;
    private String username;
    private ProcessState state;

}
