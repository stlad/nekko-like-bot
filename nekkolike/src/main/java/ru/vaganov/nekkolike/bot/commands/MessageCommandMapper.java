package ru.vaganov.nekkolike.bot.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@RequiredArgsConstructor
@Component
public class MessageCommandMapper {

    public BotCommand extractCommand(Update update) {
        if (update.getMessage() != null
                && update.getMessage().getText() != null) {
            if (update.getMessage().getText().startsWith("/")) {
                return BotCommand.fromString(getParamFromMessage(update));
            } else {
                return BotCommand.USER_MESSAGE;
            }
        }
        return BotCommand.NONE;
    }

    private String getParamFromCallback(Update update) {
        return update.getCallbackQuery().getData();
    }

    private String getParamFromMessage(Update update) {
        return update.getMessage().getText();
    }
}
