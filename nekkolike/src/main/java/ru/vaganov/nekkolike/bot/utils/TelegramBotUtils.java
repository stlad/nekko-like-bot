package ru.vaganov.nekkolike.bot.utils;

import org.telegram.telegrambots.meta.api.objects.Update;

public class TelegramBotUtils {

    public static String extractChatId(Update update) {
        if (update.getMessage() != null) {
            return update.getMessage().getChatId().toString();
        } else { //update.getCallbackQuery() != null
            return update.getCallbackQuery().getMessage().getChatId().toString();
        }
    }
}
