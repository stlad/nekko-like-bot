package ru.vaganov.nekkolike.bot.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.vaganov.nekkolike.bot.BotCommand;

@RequiredArgsConstructor
@Component
public class RequestHandler {

    public BotCommand mapToCommand(Update update) {
        if (update.getMessage().hasPhoto()) {
            return BotCommand.SAVE_PHOTO;
        } else if (update.getMessage().hasText()) {
            return BotCommand.fromCallBack(update.getMessage().getText());
        }
        return BotCommand.NONE;
    }
}
