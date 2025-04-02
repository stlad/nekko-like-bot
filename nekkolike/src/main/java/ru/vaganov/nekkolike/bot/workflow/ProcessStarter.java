package ru.vaganov.nekkolike.bot.workflow;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.process.ProcessContext;

@Component
@RequiredArgsConstructor
public class ProcessStarter {
    private final ProcessContext processContext;

    public void startProcess(Long chatId) {
        var process = processContext.getProcess(chatId);
        new StartState().start(process);
    }
}
