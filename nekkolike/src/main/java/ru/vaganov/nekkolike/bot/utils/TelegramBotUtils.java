package ru.vaganov.nekkolike.bot.utils;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.vaganov.nekkolike.bot.exceptions.FileProcessingException;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TelegramBotUtils {

    private static Long extractChatId(Update update) {
        if (update.getMessage() != null) {
            return update.getMessage().getChatId();
        } else {
            return update.getCallbackQuery().getMessage().getChatId();
        }
    }

    private static String extractTelegramUserName(Update update) {
        if (update.getMessage() != null) {
            return update.getMessage().getFrom().getUserName();
        } else {
            return update.getCallbackQuery().getMessage().getFrom().getUserName();
        }
    }

    private static File extractPhoto(Update update, TelegramLongPollingBot bot) {
        PhotoSize telegramPhoto = null;
        if (update.getMessage() != null && update.getMessage().getPhoto() != null) {
            telegramPhoto = update.getMessage().getPhoto()
                    .stream().max(Comparator.comparing(PhotoSize::getFileSize))
                    .orElse(null);
        }
        if (telegramPhoto == null) {
            return null;
        }
        File photoFile = null;
        try {
            var telegramFile = bot.execute(new GetFile(telegramPhoto.getFileId()));
            photoFile = bot.downloadFile(telegramFile);
        } catch (TelegramApiException exception) {
            throw new FileProcessingException("Не удалось сохранить файл " + telegramPhoto.getFileId(), exception);
        }
        return photoFile;
    }

    private static String[] extractParams(Update update) {
        if (update.getCallbackQuery() == null) {
            return new String[0];
        }

        var params = update.getCallbackQuery().getData().split("/");
        if (params.length <= 1) {
            return new String[0];
        }

        String[] result = new String[params.length - 1];
        System.arraycopy(params, 1, result, 0, result.length);
        return result;
    }

    public static UpdateData createUpdateData(Update update, TelegramLongPollingBot bot) {
        var chatId = extractChatId(update);
        var userName = extractTelegramUserName(update);
        var photoFile = extractPhoto(update, bot);
        var params = extractParams(update);
        String messageText = update.getMessage() == null ? null : update.getMessage().getText();

        return new UpdateData(chatId, userName, photoFile, messageText, params);
    }
}
