package ru.vaganov.nekkolike.bot.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.vaganov.nekkolike.bot.commands.BotCommand;
import ru.vaganov.nekkolike.bot.utils.TelegramBotUtils;

import java.util.List;

@Slf4j
@Component
public class ResponseMessageProcessor {

    public SendObjectWrapper prepareErrorResponse(Update update, String message) {

        return new SendObjectWrapper(
                SendMessage.builder()
                        .text(message)
                        .chatId(TelegramBotUtils.extractChatId(update))
                        .build(),
                update
        );
    }


    public SendObjectWrapper prepareMainMenu(Update update) {
        var chatID = TelegramBotUtils.extractChatId(update);
        var message = SendMessage.builder()
                .chatId(chatID)
                .text("Главное меню");

        var menu = new InlineKeyboardButton("меню");
        menu.setCallbackData(BotCommand.MOVE_TO_MAIN_MENU.getCallbackPrefix());
        var photos = new InlineKeyboardButton("Посмотреть мои картинки");
        photos.setCallbackData(BotCommand.GET_PHOTO.getCallbackPrefix());

        var buttons = List.of(menu, photos);
        var rows = List.of(buttons);
        message.replyMarkup(new InlineKeyboardMarkup(rows));
        return new SendObjectWrapper(message.build(), update);
    }

    public SendObjectWrapper preparePhotoWithNextPrevButtons(SendPhoto photo, Update update) {
        var menu = new InlineKeyboardButton("меню");
        menu.setCallbackData(BotCommand.MOVE_TO_MAIN_MENU.getCallbackPrefix());
        var prevPhoto = new InlineKeyboardButton("Назад");
        prevPhoto.setCallbackData(BotCommand.GET_PHOTO_PREV.getCallbackPrefix());
        var nextPhoto = new InlineKeyboardButton("Далее");
        nextPhoto.setCallbackData(BotCommand.GET_PHOTO_NEXT.getCallbackPrefix());

        var prevNextRow = List.of(prevPhoto, nextPhoto);
        var menuRow = List.of(menu);
        var rows = List.of(prevNextRow, menuRow);
        photo.setReplyMarkup(new InlineKeyboardMarkup(rows));
        return new SendObjectWrapper(photo, update);
    }

    public SendObjectWrapper preparePhotoWithMenuButton(SendMessage message, Update update) {
        var menu = new InlineKeyboardButton("меню");
        menu.setCallbackData(BotCommand.MOVE_TO_MAIN_MENU.getCallbackPrefix());

        var menuRow = List.of(menu);
        var rows = List.of(menuRow);
        message.setReplyMarkup(new InlineKeyboardMarkup(rows));
        return new SendObjectWrapper(message, update);
    }
}
