package ru.vaganov.nekkolike.bot.commands;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

@RequiredArgsConstructor
public class MessageCommandMapper {

    public static BotCommand extractCommand(Update update) {
        if (update.getMessage() != null && update.getMessage().hasPhoto()) {
            return BotCommand.SAVE_PHOTO;
        } else if (update.getCallbackQuery() != null) {
            return BotCommand.fromString(getParamFromCallback(update));
        } else if (update.getMessage() != null) {
            return BotCommand.fromString(getParamFromMessage(update));
        }
        return BotCommand.NONE;
    }

    private static String getParamFromCallback(Update update) {
        return update.getCallbackQuery().getData();
    }

    private static String getParamFromMessage(Update update) {
        return update.getMessage().getText();
    }
}
