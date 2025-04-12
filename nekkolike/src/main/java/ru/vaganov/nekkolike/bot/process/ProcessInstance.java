package ru.vaganov.nekkolike.bot.process;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProcessInstance {
    private final Long id;

    @Setter
    private String username;
    @Setter
    private ProcessState state;

    public static ProcessInstance create(Long id, String username, ProcessState initState) {
        return new ProcessInstance(id, username, initState);
    }
}
