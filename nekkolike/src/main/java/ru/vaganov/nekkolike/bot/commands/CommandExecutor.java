package ru.vaganov.nekkolike.bot.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.vaganov.nekkolike.bot.commands.photos.PhotoCommandExecutor;
import ru.vaganov.nekkolike.bot.response.ResponseMessageProcessor;
import ru.vaganov.nekkolike.bot.response.SendObjectWrapper;

@Component
@RequiredArgsConstructor
public class CommandExecutor {

    private final PhotoCommandExecutor photoCommandExecutor;
    private final ResponseMessageProcessor responseMessageProcessor;

    public SendObjectWrapper executeCommand(BotCommand command, Update update, TelegramLongPollingBot bot) {
        switch (command) {
            case SAVE_PHOTO -> {
                var response = photoCommandExecutor.executeSavePhoto(update, bot);
                return new SendObjectWrapper(response, update);
            }
            default -> {
                return responseMessageProcessor.prepareErrorResponse(update, "Не удалось обработать команду");
            }
        }
    }
}
