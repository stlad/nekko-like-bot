package ru.vaganov.nekkolike.bot.response;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
public class SendObjectWrapper {

    public SendObjectWrapper(SendMessage sendMessage, Update update) {
        this.sendMessage = sendMessage;
        this.update = update;
    }

    public SendObjectWrapper(SendPhoto sendPhoto, Update update) {
        this.sendPhoto = sendPhoto;
        this.update = update;
    }

    private SendMessage sendMessage;
    private SendPhoto sendPhoto;
    private final Update update;
}
