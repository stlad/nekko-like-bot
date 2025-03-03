package ru.vaganov.nekkolike.bot.commands.photos;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.vaganov.nekkolike.contentmanager.ContentManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Comparator;

@Slf4j
@Component
@RequiredArgsConstructor
public class PhotoCommandExecutor {

    private final ContentManager contentManager;

    public SendMessage executeSavePhoto(Update update, TelegramLongPollingBot bot) {
        var photo = update.getMessage().getPhoto().stream().max(Comparator.comparing(PhotoSize::getFileSize)).orElseThrow();
        try {
            var fileId = photo.getFileId();

            var getfile = new GetFile(fileId);
            getfile.setFileId(fileId);
            var telegramRemoteFilePath = bot.execute(getfile).getFilePath();
            var filename = String.format("%s/%s.jpg", update.getMessage().getChatId(), photo.getFileUniqueId());
            var targetFile = File.createTempFile(filename, ".tmp");

            log.info("Обработка фото с id: {} из чата: {}", photo.getFileId(), update.getMessage().getChatId());
            bot.downloadFile(telegramRemoteFilePath, targetFile);
            contentManager.save(filename, new FileInputStream(targetFile));

            targetFile.deleteOnExit();
            return SendMessage.builder()
                    .chatId(update.getMessage().getChatId())
                    .text("Ваше фото сохранено")
                    .build();
        } catch (TelegramApiException e) {
            log.error("Не удалось скачать файл {}", photo.getFileId(), e);
            return SendMessage.builder()
                    .text("Не удалось сохранить фото")
                    .chatId(update.getMessage().getChatId())
                    .build();
        } catch (IOException e) {
            log.error("Не удалось сохранить файл {}", photo.getFileId(), e);
            return SendMessage.builder()
                    .text("Не удалось сохранить фото")
                    .chatId(update.getMessage().getChatId())
                    .build();
        }
    }

//    private SendPhoto getPhoto(Update update, TelegramLongPollingBot bot) throws TelegramApiException {
//        try {
//            var message = SendMessage.builder()
//                    .text("Ваши картинки:")
//                    .chatId(update.getMessage().getChatId())
//                    .build();
//            execute(message);
//
//            var photos = contentManager.loadAllFiles(update.getMessage().getChatId().toString());
//            for (var file : photos) {
//                var sendPhoto = new SendPhoto(update.getMessage().getChatId().toString(), new InputFile(file));
//                execute(sendPhoto);
//            }
//        } catch (IOException exception) {
//            var errorMessage = responseMessageProcessor.prepareErrorResponse(update, exception.getMessage());
//            logger.error(exception.getMessage());
//            execute(errorMessage);
//        }
//    }
}
