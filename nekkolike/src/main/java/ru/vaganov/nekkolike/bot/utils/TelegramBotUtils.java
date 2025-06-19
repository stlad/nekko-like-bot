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

    public static String extractTelegramUserName(Update update) {
        if (update.getMessage() != null) {
            return update.getMessage().getFrom().getUserName();
        } else {
            return update.getCallbackQuery().getMessage().getFrom().getUserName();
        }
    }

    public static UpdateData createUpdateData(Update update) {
        var chatId = extractChatId(update);
        var userName = extractTelegramUserName(update);
        PhotoSize photo = null;
        if (update.getMessage() != null && update.getMessage().getPhoto() != null) {
            photo = update.getMessage().getPhoto()
                    .stream().max(Comparator.comparing(PhotoSize::getFileSize))
                    .orElse(null);
        }
        String messageText = update.getMessage() == null ? null : update.getMessage().getText();

        return new UpdateData(chatId, userName, photo, messageText);
    }
}
