package ru.vaganov.nekkolike.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.bot.NekkoBot;
import ru.vaganov.nekkolike.bot.commands.BotCommand;
import ru.vaganov.nekkolike.bot.exceptions.CommandNotFoundException;
import ru.vaganov.nekkolike.bot.exceptions.CommandProcessingFailedException;
import ru.vaganov.nekkolike.bot.exceptions.FileProcessingException;
import ru.vaganov.nekkolike.bot.response.MessageBuilder;
import ru.vaganov.nekkolike.bot.utils.UpdateData;
import ru.vaganov.nekkolike.business.process.workflow.register.RegisterStep;
import ru.vaganov.nekkolike.business.process.workflow.register.RegistrationFlow;
import ru.vaganov.nekkolike.business.process.workflow.repository.WorkflowRepository;

@Component
@RequiredArgsConstructor
public class CommandExecutor {
    private final WorkflowRepository workflowRepository;
    private final RegistrationFlow registrationFlow;

    public void executeCommand(BotCommand command, UpdateData updateData, NekkoBot bot) {
        try {
            chooseExecution(command, updateData, bot);
        } catch (FileProcessingException | CommandNotFoundException exception) {
            bot.send(MessageBuilder.errorResponse(updateData.chatId(), exception.getMessage()));
        }
    }

    private void chooseExecution(BotCommand command, UpdateData updateData, NekkoBot bot) {
        switch (command) {
            case START -> {
                registrationFlow.joined(updateData.chatId(), bot);
            }
            case USER_MESSAGE -> {
                chooseStepUserMessage(updateData, bot);
            }
            default -> {
                throw new CommandProcessingFailedException();
            }
        }
    }

    private void chooseStepUserMessage(UpdateData updateData, NekkoBot bot) {
        var step = workflowRepository.findCurrentStepByChatId(updateData.chatId()).orElseThrow();
        if (step instanceof RegisterStep) {
            switch ((RegisterStep) step) {
                case WAIT_FOR_NAME -> {
                    registrationFlow.waitForUsername(updateData.chatId(), updateData.messageText(), bot);
                }
                default -> {
                    throw new CommandProcessingFailedException();
                }
            }
        }
    }
}
