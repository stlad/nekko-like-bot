package ru.vaganov.nekkolike.bot.response;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.function.ThrowingConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
public class ResponseSender {
    private final ThrowingConsumer<SendMessage> sendMessage;
    private final ThrowingConsumer<SendPhoto> sendPhoto;

    public void send(SendObjectWrapper wrapper) {
        try {
            if (wrapper.getSendMessage() != null) {
                sendMessage.accept(wrapper.getSendMessage());
            }
            if (wrapper.getSendPhoto() != null) {
                sendPhoto.accept(wrapper.getSendPhoto());
            }
        } catch (Throwable e) {
            log.error("Ошибка при отправке сообщения в чат {}", wrapper.getUpdate().getMessage().getChatId());
        }
    }
}
