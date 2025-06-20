package ru.vaganov.nekkolike.business.process.workflow.command.addcat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.bot.exceptions.CommandProcessingFailedException;
import ru.vaganov.nekkolike.bot.exceptions.WorkflowNotFoundException;
import ru.vaganov.nekkolike.bot.response.MessageBuilder;
import ru.vaganov.nekkolike.bot.response.TelegramMessageSender;
import ru.vaganov.nekkolike.bot.utils.UpdateData;
import ru.vaganov.nekkolike.business.process.workflow.WorkflowStep;
import ru.vaganov.nekkolike.business.process.workflow.command.WorkflowCommand;
import ru.vaganov.nekkolike.business.process.workflow.repository.WorkflowRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class AddCatAcceptCommand implements WorkflowCommand {
    private final WorkflowRepository workflowRepository;

    @Override
    public void execute(UpdateData data, TelegramMessageSender sender) {
        var chatId = data.chatId();
        var flow = workflowRepository.findByChatId(chatId).orElseThrow(() -> new WorkflowNotFoundException(chatId));
        var catIdToAccept = flow.getCatRegistrationDto().getCatId().toString();
        if (data.params().length == 0
                || catIdToAccept == null
                || !catIdToAccept.equals(data.params()[0])
        ) {
            throw new CommandProcessingFailedException();
        }

        log.info("Пользователь {} подтвердил создание котика", chatId);

        sender.send(MessageBuilder.catCreated(chatId));
        sender.send(MessageBuilder.mainMenu(chatId));

        //TODO Сохранение котика в сервис

        flow.setCurrentStep(WorkflowStep.ADD_CAT_COMPLETED);
        workflowRepository.saveFlow(flow);
    }

    @Override
    public WorkflowStep getInitStep() {
        return WorkflowStep.ADD_CAT_ACCEPTED;
    }
}
