package ru.vaganov.nekkolike.bot.config;

import org.junit.jupiter.api.function.ThrowingConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import ru.vaganov.nekkolike.bot.response.ResponseSender;

@Configuration
public class BotConfig {

    @Bean
    ResponseSender getResponseSender(TelegramLongPollingBot bot){
        ThrowingConsumer<SendMessage> sendMessage = bot::execute;
        ThrowingConsumer<SendPhoto> sendPhoto = bot::execute;
        return new ResponseSender(sendMessage, sendPhoto);
    }
}
