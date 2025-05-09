package ru.vaganov.nekkolike.bot.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.vaganov.nekkolike.bot.commands.BotCommand;
import ru.vaganov.nekkolike.bot.utils.SendObjectWrapper;

import java.util.List;

@Slf4j
@Component
public class MessageBuilder {

    public static SendObjectWrapper errorResponse(Long chatId, String message) {

        return new SendObjectWrapper(
                SendMessage.builder()
                        .text(message)
                        .chatId(chatId)
                        .build(),
                chatId
        );
    }

    public static SendObjectWrapper mainMenu(Long chatId) {
        var message = SendMessage.builder()
                .chatId(chatId)
                .text(MessageTemplate.apply(MessageTemplate.MAIN_MENU));

        var menu = new InlineKeyboardButton(MessageTemplate.apply(MessageTemplate.MAIN_MENU));
        menu.setCallbackData(BotCommand.MOVE_TO_MAIN_MENU.getCallbackPrefix());

        var addCat = new InlineKeyboardButton(MessageTemplate.apply(MessageTemplate.ADD_CAT));
        addCat.setCallbackData(BotCommand.ADD_CAT.getCallbackPrefix());

        var showCats = new InlineKeyboardButton(MessageTemplate.apply(MessageTemplate.SHOW_CATS));
        showCats.setCallbackData(BotCommand.SHOW_CATS.getCallbackPrefix());

        var myCats = new InlineKeyboardButton(MessageTemplate.apply(MessageTemplate.MY_CATS));
        myCats.setCallbackData(BotCommand.MY_CATS.getCallbackPrefix());

        var buttons1 = List.of(addCat, showCats);
        var buttons2 = List.of(menu);
        var rows = List.of(buttons1, buttons2);

        message.replyMarkup(new InlineKeyboardMarkup(rows));
        return new SendObjectWrapper(message.build(), chatId);
    }

    public static SendObjectWrapper textWithMenuButton(Long chatId, String text) {
        var menu = new InlineKeyboardButton(MessageTemplate.apply(MessageTemplate.MAIN_MENU));
        menu.setCallbackData(BotCommand.MOVE_TO_MAIN_MENU.getCallbackPrefix());

        var menuRow = List.of(menu);
        var rows = List.of(menuRow);

        var message = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(new InlineKeyboardMarkup(rows))
                .build();

        return new SendObjectWrapper(message, chatId);
    }

    public static SendObjectWrapper askForName(Long chatId) {
        var message = SendMessage.builder()
                .chatId(chatId)
                .text(MessageTemplate.apply(MessageTemplate.ASK_NAME))
                .build();

        return new SendObjectWrapper(message, chatId);
    }

    public static SendObjectWrapper greetingsText(Long chatId, String username) {
        var message = SendMessage.builder()
                .chatId(chatId)
                .text(MessageTemplate.apply(MessageTemplate.GREETINGS, username))
                .build();

        return new SendObjectWrapper(message, chatId);
    }
}
