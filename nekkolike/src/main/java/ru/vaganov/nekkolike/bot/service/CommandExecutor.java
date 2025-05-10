package ru.vaganov.nekkolike.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.bot.NekkoBot;
import ru.vaganov.nekkolike.bot.commands.BotCommand;
import ru.vaganov.nekkolike.bot.exceptions.FileProcessingException;
import ru.vaganov.nekkolike.bot.response.MessageBuilder;
import ru.vaganov.nekkolike.bot.utils.UpdateData;
import ru.vaganov.nekkolike.business.process.NekkoProcessState;
import ru.vaganov.nekkolike.processengine.context.ProcessContext;

@Component
@RequiredArgsConstructor
public class CommandExecutor {
    private final ProcessContext processContext;

    public void executeCommand(BotCommand command, UpdateData updateData, NekkoBot bot) {
        try {
            chooseExecution(command, updateData, bot);
        } catch (FileProcessingException exception) {
            bot.send(MessageBuilder.errorResponse(updateData.chatId(), exception.getMessage()));
        }
    }

    private void chooseExecution(BotCommand command, UpdateData updateData, NekkoBot bot) {
        switch (command) {
            case START -> {
                processContext.runNextState(updateData.chatId(), NekkoProcessState.START, updateData.toArgs());
            }
            case USER_MESSAGE -> {
                processContext.continueCurrentState(updateData.chatId(), updateData.toArgs());
            }
            default -> {
                bot.send(MessageBuilder.errorResponse(updateData.chatId(), "Не удалось обработать команду"));
            }
        }
    }
}
