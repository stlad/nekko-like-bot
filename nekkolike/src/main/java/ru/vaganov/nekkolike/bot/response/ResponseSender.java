package ru.vaganov.nekkolike.bot.response;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import ru.vaganov.nekkolike.bot.utils.SendObjectWrapper;

@Slf4j
@RequiredArgsConstructor
@Component
public class ResponseSender {

    public void send(SendObjectWrapper wrapper, TelegramLongPollingBot bot) {
        try {
            if (wrapper.getSendMessage() != null) {
                bot.execute(wrapper.getSendMessage());
            }
            if (wrapper.getSendPhoto() != null) {
                bot.execute(wrapper.getSendPhoto());
            }
        } catch (Throwable e) {
            log.error("Ошибка при отправке сообщения в чат {}", wrapper.getChatId(), e);
        }
    }
}
