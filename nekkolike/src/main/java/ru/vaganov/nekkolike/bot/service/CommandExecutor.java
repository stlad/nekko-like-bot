package ru.vaganov.nekkolike.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import ru.vaganov.nekkolike.bot.commands.BotCommand;
import ru.vaganov.nekkolike.bot.exceptions.FileProcessingException;
import ru.vaganov.nekkolike.bot.response.MessageBuilder;
import ru.vaganov.nekkolike.bot.service.photo.PhotoCommandExecutor;
import ru.vaganov.nekkolike.bot.utils.SendObjectWrapper;
import ru.vaganov.nekkolike.bot.utils.UpdateData;

@Component
@RequiredArgsConstructor
public class CommandExecutor {

    private final PhotoCommandExecutor photoCommandExecutor;
    private final MessageBuilder messageBuilder;

    public SendObjectWrapper executeCommand(BotCommand command, UpdateData updateData, TelegramLongPollingBot bot) {
        try {
            return chooseExecution(command, updateData, bot);
        } catch (FileProcessingException exception) {
            return MessageBuilder.errorResponse(updateData.chatId(), exception.getMessage());
        }
    }

    private SendObjectWrapper chooseExecution(BotCommand command, UpdateData updateData, TelegramLongPollingBot bot) {
        var chatId = updateData.chatId();
        switch (command) {
            case SAVE_PHOTO -> {
                photoCommandExecutor.savePhoto(chatId, updateData.photo().getFileId(), bot);
                return MessageBuilder.textWithMenuButton(chatId, "Ваше фото сохранено");
            }
            case GET_PHOTO -> {
                var photo = photoCommandExecutor.getFirstPhoto(chatId);
                return MessageBuilder.photoWithNextPrevButtons(chatId, photo);
            }
            case GET_PHOTO_NEXT -> {
                var photo = photoCommandExecutor.getNextPhoto(chatId);
                return MessageBuilder.photoWithNextPrevButtons(chatId, photo);
            }
            case GET_PHOTO_PREV -> {
                var photo = photoCommandExecutor.getPrevPhoto(chatId);
                return MessageBuilder.photoWithNextPrevButtons(chatId, photo);
            }
            case MOVE_TO_MAIN_MENU -> {
                return MessageBuilder.mainMenu(chatId);
            }
            default -> {
                return MessageBuilder.errorResponse(chatId, "Не удалось обработать команду");
            }
        }
    }
}
