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

    public SendObjectWrapper executeCommand(BotCommand command, Update update, TelegramLongPollingBot bot) {
        try {
            return chooseExecution(command, update, bot);
        } catch (FileProcessingException exception) {
            return responseMessageProcessor.prepareErrorResponse(update, exception.getMessage());
        }
    }

    private SendObjectWrapper chooseExecution(BotCommand command, Update update, TelegramLongPollingBot bot) {
        switch (command) {
            case SAVE_PHOTO -> {
                var response = photoCommandExecutor.savePhoto(update, bot);
                return responseMessageProcessor.preparePhotoWithMenuButton(response, update);
            }
            case GET_PHOTO -> {
                var response = photoCommandExecutor.getFirstPhoto(update, bot);
                return responseMessageProcessor.preparePhotoWithNextPrevButtons(response, update);
            }
            case GET_PHOTO_NEXT -> {
                var response = photoCommandExecutor.getNextPhoto(update, bot);
                return responseMessageProcessor.preparePhotoWithNextPrevButtons(response, update);
            }
            case GET_PHOTO_PREV -> {
                var response = photoCommandExecutor.getPrevPhoto(update, bot);
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
