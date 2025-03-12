package ru.vaganov.nekkolike.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.vaganov.nekkolike.bot.commands.BotCommand;
import ru.vaganov.nekkolike.bot.exceptions.FileProcessingException;
import ru.vaganov.nekkolike.bot.response.ResponseMessageProcessor;
import ru.vaganov.nekkolike.bot.response.SendObjectWrapper;
import ru.vaganov.nekkolike.bot.service.photo.PhotoCommandExecutor;

@Component
@RequiredArgsConstructor
public class CommandExecutor {

    private final PhotoCommandExecutor photoCommandExecutor;
    private final ResponseMessageProcessor responseMessageProcessor;

    public SendObjectWrapper executeCommand(String chatId, BotCommand command, Update update, TelegramLongPollingBot bot) {
        try {
            return chooseExecution(chatId, command, update, bot);
        } catch (FileProcessingException exception) {
            return responseMessageProcessor.prepareErrorResponse(update, exception.getMessage());
        }
    }

    private SendObjectWrapper chooseExecution(String chatId, BotCommand command, Update update, TelegramLongPollingBot bot) {
        switch (command) {
            case SAVE_PHOTO -> {
                var response = photoCommandExecutor.savePhoto(chatId, update, bot);
                return responseMessageProcessor.preparePhotoWithMenuButton(response, update);
            }
            case GET_PHOTO -> {
                var response = photoCommandExecutor.getFirstPhoto(chatId);
                return responseMessageProcessor.preparePhotoWithNextPrevButtons(response, update);
            }
            case GET_PHOTO_NEXT -> {
                var response = photoCommandExecutor.getNextPhoto(chatId);
                return responseMessageProcessor.preparePhotoWithNextPrevButtons(response, update);
            }
            case GET_PHOTO_PREV -> {
                var response = photoCommandExecutor.getPrevPhoto(chatId);
                return responseMessageProcessor.preparePhotoWithNextPrevButtons(response, update);
            }
            case MOVE_TO_MAIN_MENU -> {
                return responseMessageProcessor.prepareMainMenu(update);
            }
            default -> {
                return responseMessageProcessor.prepareErrorResponse(update, "Не удалось обработать команду");
            }
        }
    }
}
