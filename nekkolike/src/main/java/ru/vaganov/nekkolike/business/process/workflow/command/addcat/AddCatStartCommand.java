package ru.vaganov.nekkolike.business.process.workflow.command.addcat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.bot.response.MessageBuilder;
import ru.vaganov.nekkolike.bot.response.TelegramMessageSender;
import ru.vaganov.nekkolike.bot.utils.UpdateData;
import ru.vaganov.nekkolike.business.process.workflow.UserWorkflow;
import ru.vaganov.nekkolike.business.process.workflow.WorkflowStep;
import ru.vaganov.nekkolike.business.process.workflow.command.WorkflowCommand;
import ru.vaganov.nekkolike.business.process.workflow.repository.WorkflowRepository;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AddCatStartCommand implements WorkflowCommand {
    private final WorkflowRepository workflowRepository;

    @Override
    public void execute(UpdateData data, TelegramMessageSender sender) {
        var chatId = data.chatId();
        log.info("Пользователь {} начал процесс создания котика", chatId);
        var flow = workflowRepository.findByChatId(chatId).orElse(new UserWorkflow(chatId));
        flow.setCurrentStep(WorkflowStep.ADD_CAT_STARTED);

        flow.initCat(UUID.randomUUID());
        sender.send(MessageBuilder.askForCatPhoto(chatId));

        log.info("Ожидается фото котика пользователя от {}", chatId);
        flow.setCurrentStep(WorkflowStep.ADD_CAT_WAIT_FOR_PHOTO);
        workflowRepository.saveFlow(flow);
    }

    @Override
    public WorkflowStep getInitStep() {
        return WorkflowStep.ADD_CAT_STARTED;
    }
}
