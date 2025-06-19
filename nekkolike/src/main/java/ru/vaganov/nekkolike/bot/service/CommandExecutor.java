package ru.vaganov.nekkolike.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.bot.NekkoBot;
import ru.vaganov.nekkolike.bot.commands.BotCommand;
import ru.vaganov.nekkolike.bot.exceptions.CommandNotFoundException;
import ru.vaganov.nekkolike.bot.exceptions.CommandProcessingFailedException;
import ru.vaganov.nekkolike.bot.exceptions.FileProcessingException;
import ru.vaganov.nekkolike.bot.exceptions.WorkflowNotFoundException;
import ru.vaganov.nekkolike.bot.response.MessageBuilder;
import ru.vaganov.nekkolike.bot.utils.UpdateData;
import ru.vaganov.nekkolike.business.process.workflow.repository.WorkflowRepository;
import ru.vaganov.nekkolike.business.process.workflow.service.AddCatFlow;
import ru.vaganov.nekkolike.business.process.workflow.service.RegistrationFlow;

@Component
@RequiredArgsConstructor
public class CommandExecutor {
    private final WorkflowRepository workflowRepository;
    private final RegistrationFlow registrationFlow;
    private final AddCatFlow addCatFlow;

    public void executeCommand(BotCommand command, UpdateData updateData, NekkoBot bot) {
        try {
            chooseExecution(command, updateData, bot);
        } catch (FileProcessingException | CommandNotFoundException | WorkflowNotFoundException exception) {
            bot.send(MessageBuilder.errorResponse(updateData.chatId(), exception.getMessage()));
        }
    }

    private void chooseExecution(BotCommand command, UpdateData updateData, NekkoBot bot) {
        switch (command) {
            case START -> {
                registrationFlow.joined(updateData.chatId(), bot);
            }
            case ADD_CAT -> {
                addCatFlow.addCatStarted(updateData.chatId(), bot);
            }
            case ADD_CAT_ACCEPT -> {
                addCatFlow.catAccepted(updateData.chatId(), bot);
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
        var step = workflowRepository.findCurrentStepByChatId(updateData.chatId())
                .orElseThrow(() -> new WorkflowNotFoundException(updateData.chatId()));

        switch (step) {
            case JOIN_WAIT_FOR_NAME -> {
                registrationFlow.waitForUsername(updateData.chatId(), updateData.messageText(), bot);
            }
            case ADD_CAT_WAIT_FOR_PHOTO -> {
                addCatFlow.catPhotoAdded(updateData.chatId(), updateData.photo().getFileId(), bot);
            }
            case ADD_CAT_WAIT_FOR_NAME -> {
                addCatFlow.catNameAdded(updateData.chatId(), updateData.messageText(),
                        updateData.telegramUsername(), bot);
            }
            default -> {
                throw new CommandProcessingFailedException();
            }
        }

    }
}
