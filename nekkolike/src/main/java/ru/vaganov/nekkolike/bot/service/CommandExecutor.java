package ru.vaganov.nekkolike.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.bot.commands.BotCommand;
import ru.vaganov.nekkolike.bot.exceptions.CommandNotFoundException;
import ru.vaganov.nekkolike.bot.exceptions.CommandProcessingFailedException;
import ru.vaganov.nekkolike.bot.exceptions.FileProcessingException;
import ru.vaganov.nekkolike.bot.exceptions.WorkflowNotFoundException;
import ru.vaganov.nekkolike.bot.response.MessageBuilder;
import ru.vaganov.nekkolike.bot.response.TelegramMessageSender;
import ru.vaganov.nekkolike.bot.utils.UpdateData;
import ru.vaganov.nekkolike.business.process.workflow.WorkflowStep;
import ru.vaganov.nekkolike.business.process.workflow.command.WorkflowProvider;
import ru.vaganov.nekkolike.business.process.workflow.repository.WorkflowRepository;

@Component
@RequiredArgsConstructor
public class CommandExecutor {
    private final WorkflowProvider commandProvider;
    private final WorkflowRepository workflowRepository;

    public void executeCommand(BotCommand command, UpdateData updateData, TelegramMessageSender bot) {
        try {
            chooseExecution(command, updateData, bot);
        } catch (FileProcessingException | CommandNotFoundException
                 | WorkflowNotFoundException | CommandProcessingFailedException exception) {
            bot.send(MessageBuilder.errorResponse(updateData.chatId(), exception.getMessage()));
        }
    }

    private void chooseExecution(BotCommand command, UpdateData updateData, TelegramMessageSender sender) {
        switch (command) {
            case MOVE_TO_MAIN_MENU -> {
                sender.send(MessageBuilder.mainMenu(updateData.chatId()));
            }
            case START -> {
                commandProvider.execute(WorkflowStep.JOIN_STARTED, updateData, sender);
            }
            case ADD_CAT -> {
                commandProvider.execute(WorkflowStep.ADD_CAT_STARTED, updateData, sender);
            }
            case ADD_CAT_ACCEPT -> {
                commandProvider.execute(WorkflowStep.ADD_CAT_ACCEPTED, updateData, sender);
            }
            case USER_MESSAGE -> {
                var step = workflowRepository.findCurrentStepByChatId(updateData.chatId())
                        .orElseThrow(() -> new WorkflowNotFoundException(updateData.chatId()));

                commandProvider.execute(step, updateData, sender);
            }
            default -> {
                throw new CommandProcessingFailedException();
            }
        }
    }
}
