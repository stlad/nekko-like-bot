package ru.vaganov.nekkolike.bot.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ResponseMessageProcessor {

    public SendObjectWrapper prepareErrorResponse(Update update, String message) {

        return new SendObjectWrapper(
                SendMessage.builder()
                        .text(message)
                        .chatId(update.getMessage().getChatId())
                        .build(),
                update
        );
    }


    private SendMessage drawButtonMenu(Update update) throws TelegramApiException {
        var message = SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text("Главное меню");

        var replyKeyboardMarkup = new ReplyKeyboardMarkup();
        var buttons = List.of(new KeyboardButton("Посмотреть мои картинки"));
        var rows = List.of(new KeyboardRow(buttons));

        replyKeyboardMarkup.setKeyboard(rows);
        message.replyMarkup(replyKeyboardMarkup);
        return message.build();
    }

}
