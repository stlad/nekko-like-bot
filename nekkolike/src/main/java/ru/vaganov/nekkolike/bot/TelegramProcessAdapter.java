package ru.vaganov.nekkolike.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.process.bot.workflow.ProcessStarter;

@Component
@RequiredArgsConstructor
public class TelegramProcessAdapter {
    private final ProcessStarter processStarter;

    public void startProcess(Long telegramChatId) {
        //TODO создать пользователя и привязать к нему бизнес-процесс
        processStarter.startProcess(telegramChatId.toString());
    }

}
