package ru.vaganov.nekkolike.bot.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.vaganov.nekkolike.bot.commands.BotCommand;
import ru.vaganov.nekkolike.bot.commands.PagingDirection;
import ru.vaganov.nekkolike.bot.utils.SendObjectWrapper;
import ru.vaganov.nekkolike.common.dto.CatListElementDto;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class MessageBuilder {
    private static final Integer ROW_SIZE = 3;

    public static SendObjectWrapper errorResponse(Long chatId, String message) {
        var menu = new InlineKeyboardButton(MessageTemplate.apply(MessageTemplate.MAIN_MENU));
        menu.setCallbackData(BotCommand.MOVE_TO_MAIN_MENU.getCallbackPrefix());

        var menuRow = List.of(menu);
        var rows = List.of(menuRow);

        return new SendObjectWrapper(
                SendMessage.builder()
                        .text(message)
                        .chatId(chatId)
                        .replyMarkup(new InlineKeyboardMarkup(rows))
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
        myCats.setCallbackData(BotCommand.MY_CATS.getCallbackPrefix() + "/" + PagingDirection.FIRST.name());

        var buttons1 = List.of(addCat, showCats, myCats);
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

    private static SendObjectWrapper simpleTextByTemplate(Long chatId, MessageTemplate template, Object... args) {
        var message = SendMessage.builder()
                .chatId(chatId)
                .text(MessageTemplate.apply(template, args))
                .build();

        return new SendObjectWrapper(message, chatId);
    }

    public static SendObjectWrapper askForName(Long chatId) {
        return simpleTextByTemplate(chatId, MessageTemplate.ASK_NAME);
    }

    public static SendObjectWrapper askForCatName(Long chatId) {
        return simpleTextByTemplate(chatId, MessageTemplate.ASK_CAT_NAME);
    }

    public static SendObjectWrapper askForCatPhoto(Long chatId) {
        return simpleTextByTemplate(chatId, MessageTemplate.ASK_CAT_PHOTO);
    }

    public static SendObjectWrapper catCreated(Long chatId) {
        return simpleTextByTemplate(chatId, MessageTemplate.ADD_CAT_CREATED);
    }

    public static SendObjectWrapper greetingsText(Long chatId, String username) {
        return simpleTextByTemplate(chatId, MessageTemplate.GREETINGS, username);
    }

    public static SendObjectWrapper acceptCatName(Long chatId, String username, String catName, UUID catId, byte[] photo) {
        var inputFile = new InputFile(new ByteArrayInputStream(photo), catId.toString());
        var message = SendPhoto.builder()
                .chatId(chatId.toString())
                .photo(inputFile)
                .caption(MessageTemplate.apply(MessageTemplate.ADD_CAT_ACCEPT_TEXT, catName, username));

        var menu = new InlineKeyboardButton(MessageTemplate.apply(MessageTemplate.MAIN_MENU));
        menu.setCallbackData(BotCommand.MOVE_TO_MAIN_MENU.getCallbackPrefix());

        var accept = new InlineKeyboardButton(MessageTemplate.apply(MessageTemplate.ACCEPT));
        accept.setCallbackData(BotCommand.ADD_CAT_ACCEPT.getCallbackPrefix() + "/" + catId.toString());

        var starFromBeginning = new InlineKeyboardButton(MessageTemplate.apply(MessageTemplate.ASK_CAT_START_FROM_BEGINNING));
        starFromBeginning.setCallbackData(BotCommand.ADD_CAT.getCallbackPrefix());


        var buttons1 = List.of(accept, starFromBeginning);
        var buttons2 = List.of(menu);
        var rows = List.of(buttons1, buttons2);


        message.replyMarkup(new InlineKeyboardMarkup(rows));
        return new SendObjectWrapper(message.build(), chatId);
    }

    public static SendObjectWrapper likeCatMenu(Long chatId, String username, String catName, UUID catId, byte[] photo,
                                                Integer likeCount, Integer dislikeCount) {
        var inputFile = new InputFile(new ByteArrayInputStream(photo), catId.toString());
        var message = SendPhoto.builder()
                .chatId(chatId.toString())
                .photo(inputFile)
                .caption(MessageTemplate.apply(MessageTemplate.ADD_CAT_ACCEPT_TEXT, catName, username));

        var menu = new InlineKeyboardButton(MessageTemplate.apply(MessageTemplate.MAIN_MENU));
        menu.setCallbackData(BotCommand.MOVE_TO_MAIN_MENU.getCallbackPrefix());

        var like = new InlineKeyboardButton(MessageTemplate.apply(MessageTemplate.SHOW_CATS_LIKE, likeCount));
        like.setCallbackData(BotCommand.SHOW_CATS_REVIEW.getCallbackPrefix() + "/LIKE" + "/" + catId.toString());

        var dislike = new InlineKeyboardButton(MessageTemplate.apply(MessageTemplate.SHOW_CATS_DISLIKE, dislikeCount));
        dislike.setCallbackData(BotCommand.SHOW_CATS_REVIEW.getCallbackPrefix() + "/DISLIKE" + "/" + catId.toString());

        var buttons1 = List.of(like, dislike);
        var buttons2 = List.of(menu);
        var rows = List.of(buttons1, buttons2);
        message.replyMarkup(new InlineKeyboardMarkup(rows));
        return new SendObjectWrapper(message.build(), chatId);
    }

    public static SendObjectWrapper catListMenu(Long chatId, List<CatListElementDto> cats) {
        var message = simpleTextByTemplate(chatId, MessageTemplate.MY_CATS_LIST);

        var menu = new InlineKeyboardButton(MessageTemplate.apply(MessageTemplate.MAIN_MENU));
        menu.setCallbackData(BotCommand.MOVE_TO_MAIN_MENU.getCallbackPrefix());

        var prev = new InlineKeyboardButton(MessageTemplate.apply(MessageTemplate.PREV));
        prev.setCallbackData(BotCommand.MY_CATS.getCallbackPrefix() + "/" + PagingDirection.PREV.name());

        var next = new InlineKeyboardButton(MessageTemplate.apply(MessageTemplate.NEXT));
        next.setCallbackData(BotCommand.MY_CATS.getCallbackPrefix() + "/" + PagingDirection.NEXT.name());

        var rows = new ArrayList<List<InlineKeyboardButton>>();
        var currentCatRow = new ArrayList<InlineKeyboardButton>();
        for (int i = 0; i < cats.size(); i++) {
            currentCatRow.add(createCatListButton(cats.get(i).catName(), cats.get(i).catId()));
            if (currentCatRow.size() == ROW_SIZE || i == cats.size() - 1) {
                rows.add(currentCatRow);
                currentCatRow = new ArrayList<>();
            }
        }

        var navigationBar = List.of(prev, next);
        var menuBar = List.of(menu);
        rows.add(navigationBar);
        rows.add(menuBar);
        message.getSendMessage().setReplyMarkup(new InlineKeyboardMarkup(rows));
        return message;
    }

    private static InlineKeyboardButton createCatListButton(String catName, UUID catId) {
        var cat = new InlineKeyboardButton(catName);
        cat.setCallbackData(BotCommand.MY_CATS_INFO.getCallbackPrefix() + "/" + catId.toString());
        return cat;
    }

    public static SendObjectWrapper catInfoMenu(Long chatId, String username, String catName, UUID catId, byte[] photo) {
        var inputFile = new InputFile(new ByteArrayInputStream(photo), catId.toString());
        var message = SendPhoto.builder()
                .chatId(chatId.toString())
                .photo(inputFile)
                .caption(MessageTemplate.apply(MessageTemplate.ADD_CAT_ACCEPT_TEXT, catName, username));

        var menu = new InlineKeyboardButton(MessageTemplate.apply(MessageTemplate.MAIN_MENU));
        menu.setCallbackData(BotCommand.MOVE_TO_MAIN_MENU.getCallbackPrefix());

        var delete = new InlineKeyboardButton(MessageTemplate.apply(MessageTemplate.MY_CATS_DELETE));
        delete.setCallbackData(BotCommand.MY_CATS_DELETE.getCallbackPrefix() + "/" + catId.toString());

        var buttons1 = List.of(delete);
        var buttons2 = List.of(menu);
        var rows = List.of(buttons1, buttons2);

        message.replyMarkup(new InlineKeyboardMarkup(rows));
        return new SendObjectWrapper(message.build(), chatId);
    }

    public static SendObjectWrapper deleteCat(Long chatId) {
        return simpleTextByTemplate(chatId, MessageTemplate.MY_CATS_DELETE);
    }
}
