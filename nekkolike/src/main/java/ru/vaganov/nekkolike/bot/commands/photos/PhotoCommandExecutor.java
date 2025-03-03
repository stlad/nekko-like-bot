package ru.vaganov.nekkolike.bot.commands.photos;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.vaganov.nekkolike.contentmanager.ContentManager;

@Slf4j
@Component
@RequiredArgsConstructor
public class PhotoCommandExecutor {

    private final ContentManager contentManager;

    public SendMessage executeSavePhoto(Update update) {
        log.info("скачкаа фото");
        return SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text("Сохианеи фото")
                .build();
//        var photo = update.getMessage().getPhoto().stream().max(Comparator.comparing(PhotoSize::getFileSize)).orElseThrow();
//        var fileId = photo.getFileId();
//        var filename = String.format("%s/%s.jpg", update.getMessage().getChatId(), photo.getFileUniqueId());
//        log.info("Processing photo with id: {} from chat: {}", photo.getFileId(), update.getMessage().getChatId());

//        var getfile = new GetFile(fileId);
//        getfile.setFileId(fileId);
//        var telegramRemoteFilePath = bot.execute(getfile).getFilePath();
//
//        var message = new SendMessage();
//        message.setChatId(update.getMessage().getChatId());
//        try {
//            var inputStream = URI.create("https://api.telegram.org/file/bot" + token + "/" + telegramRemoteFilePath)
//                    .toURL().openStream();
//            contentManager.save(filename, inputStream);
//            message.setText("Ваша картинка сохранена");
//        } catch (IOException exception) {
//            message.setText("Не удалось сохранить ваше изображение");
//            log.error(exception.getMessage());
//        }
//        bot.execute(message);
    }

//    private void getAllPhotos(Update update) throws TelegramApiException {
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
