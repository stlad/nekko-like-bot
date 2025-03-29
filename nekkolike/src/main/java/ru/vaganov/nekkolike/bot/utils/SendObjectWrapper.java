package ru.vaganov.nekkolike.bot.utils;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
public class SendObjectWrapper {

    public SendObjectWrapper(SendMessage sendMessage, Long chatId) {
        this.sendMessage = sendMessage;
    }

    public SendObjectWrapper(SendPhoto sendPhoto, Long chatId) {
        this.sendPhoto = sendPhoto;
    }

    private SendMessage sendMessage;
    private SendPhoto sendPhoto;
    private Long chatId;
}
