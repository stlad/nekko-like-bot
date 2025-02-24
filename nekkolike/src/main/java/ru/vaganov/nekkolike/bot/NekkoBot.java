package ru.vaganov.nekkolike.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.vaganov.nekkolike.contentmanager.ContentManager;

import java.io.IOException;
import java.net.URI;
import java.util.Comparator;
import java.util.List;

@Component
public class NekkoBot extends TelegramLongPollingBot {

    private final Logger logger = LoggerFactory.getLogger(NekkoBot.class);

    @Value("${telegram.name}")
    private String name;

    private String token;

    private final ContentManager contentManager;


    public NekkoBot(ContentManager contentManager, @Value("${telegram.token}") String token) {
        super(token);
        this.token = token;
        this.contentManager = contentManager;
    }


    private void processUpdate(Update update) throws TelegramApiException {
        if (update.getMessage().hasPhoto()) {
            this.downloadPhoto(update);
        }
    }

    private void downloadPhoto(Update update) throws TelegramApiException {
        var photo = update.getMessage().getPhoto().stream().max(Comparator.comparing(PhotoSize::getFileSize)).orElseThrow();
        var fileId = photo.getFileId();
        var filename = String.format("%s/%s.jpg", update.getMessage().getChatId(), photo.getFileUniqueId());
        logger.info("Processing photo with id: {} from chat: {}", photo.getFileId(), update.getMessage().getChatId());

        var getfile = new GetFile(fileId);
        getfile.setFileId(fileId);
        var telegramRemoteFilePath = execute(getfile).getFilePath();

        var message = new SendMessage();
        message.setChatId(update.getMessage().getChatId());
        try {
            var inputStream = URI.create("https://api.telegram.org/file/bot" + token + "/" + telegramRemoteFilePath)
                    .toURL().openStream();
            contentManager.save(filename, inputStream);
            message.setText("Ваша картинка сохранена");
        } catch (IOException exception) {
            message.setText("Не удалось сохранить ваше изображение");
            logger.error(exception.getMessage());
        }
        execute(message);
    }

    private void sendPhoto(Update update, InputFile photo) throws TelegramApiException {
        this.execute(SendPhoto.builder()
                .chatId(update.getMessage().getChatId())
                .photo(photo)
                .build()
        );
    }

    @Override
    public void onUpdateReceived(Update update) {
        logger.info("{} from {}", update.getMessage().getText(), update.getMessage().getChatId());
        try {
            processUpdate(update);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

}
