package ru.vaganov.nekkolike.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import ru.vaganov.nekkolike.bot.NekkoBot;
import ru.vaganov.nekkolike.bot.commands.BotCommand;
import ru.vaganov.nekkolike.bot.exceptions.FileProcessingException;
import ru.vaganov.nekkolike.bot.response.MessageBuilder;
import ru.vaganov.nekkolike.bot.utils.SendObjectWrapper;
import ru.vaganov.nekkolike.bot.utils.UpdateData;
import ru.vaganov.nekkolike.business.process.NekkoOutputMessageProvider;
import ru.vaganov.nekkolike.business.process.NekkoProcessState;
import ru.vaganov.nekkolike.processengine.ProcessEngine;

@Component
@RequiredArgsConstructor
public class CommandExecutor {
    private final ProcessEngine processEngine;

    public SendObjectWrapper executeCommand(BotCommand command, UpdateData updateData, NekkoBot bot) {
        try {
            return chooseExecution(command, updateData, bot);
        } catch (FileProcessingException exception) {
            return MessageBuilder.errorResponse(updateData.chatId(), exception.getMessage());
        }
    }

    private SendObjectWrapper chooseExecution(BotCommand command, UpdateData updateData, NekkoBot bot) {
        var out = new NekkoOutputMessageProvider(bot);
        switch (command) {
            case START -> {
                if (processEngine.getProcessInstance(updateData.chatId()).isEmpty()) {
                    processEngine.initProcess(updateData.chatId(), NekkoProcessState.START);
                }

                processEngine.runCurrentState(updateData.chatId(), out, updateData.toArgs());
                return MessageBuilder.askForName(updateData.chatId());
            }
            default -> {
                return MessageBuilder.errorResponse(updateData.chatId(), "Не удалось обработать команду");
            }
        }
    }
}
