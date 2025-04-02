package ru.vaganov.nekkolike.bot.utils;

import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Comparator;

public class TelegramBotUtils {

    public static Long extractChatId(Update update) {
        if (update.getMessage() != null) {
            return update.getMessage().getChatId();
        } else {
            return update.getCallbackQuery().getMessage().getChatId();
        }
    }

    public static UpdateData updateData(Update update) {
        var chatId = extractChatId(update);
        if (update.getMessage() != null && update.getMessage().getPhoto() != null) {
            return new UpdateData(chatId, update.getMessage().getPhoto()
                    .stream().max(Comparator.comparing(PhotoSize::getFileSize)).orElse(null));
        }

        return new UpdateData(chatId, null);
    }
}
