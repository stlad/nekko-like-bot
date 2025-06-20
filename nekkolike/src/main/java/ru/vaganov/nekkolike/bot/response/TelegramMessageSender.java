package ru.vaganov.nekkolike.bot.response;

import ru.vaganov.nekkolike.bot.utils.SendObjectWrapper;

public interface TelegramMessageSender {
    void send(SendObjectWrapper wrapper);
}
