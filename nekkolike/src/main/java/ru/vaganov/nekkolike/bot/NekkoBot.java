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
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.vaganov.nekkolike.contentmanager.ContentManager;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
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
        } else if (update.getMessage().hasText()
                && (update.getMessage().getText().equals("/show_my_photos")
                || update.getMessage().getText().equals("Посмотреть мои картинки"))) {
            getAllPhotos(update);
        } else if (update.getMessage().hasText() && update.getMessage().getText().equals("/main_menu")) {
            drawButtonMenu(update);
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

    private void getAllPhotos(Update update) throws TelegramApiException {
        try {
            var message = SendMessage.builder()
                    .text("Ваши картинки:")
                    .chatId(update.getMessage().getChatId())
                    .build();
            execute(message);

            var photos = contentManager.loadAllFiles(update.getMessage().getChatId().toString());
            for (var file : photos) {
                var sendPhoto = new SendPhoto(update.getMessage().getChatId().toString(), new InputFile(file));
                execute(sendPhoto);
            }
        } catch (IOException exception) {
            var message = SendMessage.builder()
                    .text("Не удалось сохранить ваше изображение")
                    .chatId(update.getMessage().getChatId())
                    .build();
            logger.error(exception.getMessage());
            execute(message);
        }
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

    private void drawButtonMenu(Update update) throws TelegramApiException {
        var message = SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text("Главное меню");

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        var rows = new ArrayList<KeyboardRow>();
        var buttons = new ArrayList<KeyboardButton>();
        buttons.add(new KeyboardButton("Посмотреть мои картинки"));
        rows.add(new KeyboardRow(buttons));

        replyKeyboardMarkup.setKeyboard(rows);
        message.replyMarkup(replyKeyboardMarkup);
        execute(message.build());
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
