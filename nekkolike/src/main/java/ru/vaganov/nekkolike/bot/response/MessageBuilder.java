package ru.vaganov.nekkolike.bot.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.vaganov.nekkolike.bot.commands.BotCommand;
import ru.vaganov.nekkolike.bot.utils.SendObjectWrapper;

import java.io.File;
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
                .text("Главное меню");

        var menu = new InlineKeyboardButton("меню");
        menu.setCallbackData(BotCommand.MOVE_TO_MAIN_MENU.getCallbackPrefix());
        var photos = new InlineKeyboardButton("Посмотреть мои картинки");
        photos.setCallbackData(BotCommand.GET_PHOTO.getCallbackPrefix());

        var buttons = List.of(menu, photos);
        var rows = List.of(buttons);
        message.replyMarkup(new InlineKeyboardMarkup(rows));
        return new SendObjectWrapper(message.build(), chatId);
    }

    public static SendObjectWrapper photoWithNextPrevButtons(Long chatId, File photo) {
        var menu = new InlineKeyboardButton("меню");
        menu.setCallbackData(BotCommand.MOVE_TO_MAIN_MENU.getCallbackPrefix());
        var prevPhoto = new InlineKeyboardButton("Назад");
        prevPhoto.setCallbackData(BotCommand.GET_PHOTO_PREV.getCallbackPrefix());
        var nextPhoto = new InlineKeyboardButton("Далее");
        nextPhoto.setCallbackData(BotCommand.GET_PHOTO_NEXT.getCallbackPrefix());

        var prevNextRow = List.of(prevPhoto, nextPhoto);
        var menuRow = List.of(menu);
        var rows = List.of(prevNextRow, menuRow);

        var sendPhoto = new SendPhoto(chatId.toString(), new InputFile(photo));
        sendPhoto.setReplyMarkup(new InlineKeyboardMarkup(rows));

        return new SendObjectWrapper(sendPhoto, chatId);
    }

    public static SendObjectWrapper textWithMenuButton(Long chatId, String text) {
        var menu = new InlineKeyboardButton("меню");
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
}
