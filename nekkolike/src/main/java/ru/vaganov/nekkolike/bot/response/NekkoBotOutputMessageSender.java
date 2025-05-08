package ru.vaganov.nekkolike.bot.response;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.bot.NekkoBot;
import ru.vaganov.nekkolike.business.process.io.OutputMessageSender;

@Component
@RequiredArgsConstructor
public class NekkoBotOutputMessageSender implements OutputMessageSender {
    private final NekkoBot bot;

    @Override
    public void askForName(Long chatId) {
        bot.send(MessageBuilder.askForName(chatId));
    }
}
