package ru.vaganov.nekkolike.bot.service.photo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.vaganov.nekkolike.bot.exceptions.FileProcessingException;
import ru.vaganov.nekkolike.bot.utils.TelegramBotUtils;
import ru.vaganov.nekkolike.contentmanager.ContentManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class PhotoCommandExecutor {

    private final ContentManager contentManager;

    //TODO противорчит state-less подходу. Но необходим, чтобы запомнить страницу пользователя
    private final Map<String, Integer> userPhotoPages;

    public PhotoCommandExecutor(ContentManager contentManager) {
        this.contentManager = contentManager;
        userPhotoPages = new HashMap<>();
    }

    public SendMessage savePhoto(String chatId, Update update, TelegramLongPollingBot bot) throws FileProcessingException {
        var photo = update.getMessage().getPhoto().stream().max(Comparator.comparing(PhotoSize::getFileSize)).orElseThrow();
        try {
            var fileId = photo.getFileId();

            var getfile = new GetFile(fileId);
            getfile.setFileId(fileId);
            var telegramRemoteFilePath = bot.execute(getfile).getFilePath();
            var filename = String.format("%s/%s.jpg", chatId, photo.getFileUniqueId());
            var targetFile = File.createTempFile(filename, ".tmp");

            log.info("Обработка фото с id: {} из чата: {}", photo.getFileId(), chatId);
            bot.downloadFile(telegramRemoteFilePath, targetFile);
            contentManager.save(filename, new FileInputStream(targetFile));

            targetFile.deleteOnExit();
            return SendMessage.builder()
                    .chatId(chatId)
                    .text("Ваше фото сохранено")
                    .build();
        } catch (TelegramApiException | IOException exception) {
            log.error("Не удалось сохранить файл {}", photo.getFileId(), exception);
            throw new FileProcessingException("Не удалось сохранить файл " + photo.getFileId(), exception);
        }
    }

    public SendPhoto getFirstPhoto(String chatId) {
        userPhotoPages.put(chatId, 0);

        return loadPhotoFromCM(chatId, 0);
    }

    public SendPhoto getNextPhoto(String chatId) {
        if (!userPhotoPages.containsKey(chatId)) {
            return getFirstPhoto(chatId);
        }
        var targetPage = userPhotoPages.get(chatId) + 1;
        var photo = loadPhotoFromCM(chatId, targetPage);
        userPhotoPages.put(chatId, targetPage);
        return photo;
    }

    public SendPhoto getPrevPhoto(String chatId) {
        if (!userPhotoPages.containsKey(chatId) || userPhotoPages.get(chatId) <= 1) {
            return getFirstPhoto(chatId);
        }
        var targetPage = userPhotoPages.get(chatId) - 1;
        var photo = loadPhotoFromCM(chatId, targetPage);
        userPhotoPages.put(chatId, targetPage);
        return photo;
    }

    private SendPhoto loadPhotoFromCM(String chatId, int index) {
        log.info("Загрузка фото {} для чата {}", index, chatId);
        List<File> photos;
        try {
            photos = contentManager.loadAllFiles(chatId).stream()
                    .filter(file -> file.getName().endsWith(".jpg")
                            || file.getName().endsWith(".png"))
                    .toList();
        } catch (IOException exception) {
            log.error("Не удалось получить фото для чата {}", chatId, exception);
            throw new FileProcessingException("Не удалось получить фото", exception);
        }

        if (index < 0) {
            throw new FileProcessingException("Предыдущих фотографий нет");
        }
        if (index >= photos.size()) {
            throw new FileProcessingException("Дальше фотографий нет");
        }

        return new SendPhoto(chatId, new InputFile(photos.get(index)));
    }
}
