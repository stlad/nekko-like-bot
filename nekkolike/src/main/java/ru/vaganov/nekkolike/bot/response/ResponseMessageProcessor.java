package ru.vaganov.nekkolike.bot.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.vaganov.nekkolike.bot.commands.BotCommand;

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


    public SendObjectWrapper drawMainMenu(Update update) {
        var message = SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text("Главное меню");

        var k = new InlineKeyboardButton("меню");
        k.setCallbackData(BotCommand.MOVE_TO_MAIN_MENU.getCallbackPrefix());

        var buttons = List.of(k);
        var rows = List.of(buttons);
        message.replyMarkup(new InlineKeyboardMarkup(rows));
        return new SendObjectWrapper(message.build(), update);
    }
}
