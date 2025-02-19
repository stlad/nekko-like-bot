package ru.vaganov.nekkolike.bot;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NekkoBot extends TelegramLongPollingBot {

    private final Logger logger = LoggerFactory.getLogger(NekkoBot.class);
    @Value("${telegram.token}")
    private String token;

    @Value("${telegram.name}")
    private String name;

    @Override
    public void onUpdateReceived(Update update) {
        logger.info("{} from {}", update.getMessage().getText(), update.getMessage().getChatId());
        var message = new SendMessage();
        message.setChatId(update.getMessage().getChatId());
        message.setText(update.getMessage().getText());
        try {
            this.execute(message);
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

    @Override
    public String getBotToken() {
        return token;
    }

}
