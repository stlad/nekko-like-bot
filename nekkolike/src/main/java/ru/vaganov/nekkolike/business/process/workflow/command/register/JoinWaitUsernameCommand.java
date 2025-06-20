package ru.vaganov.nekkolike.business.process.workflow.command.register;

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

@Component
@Slf4j
@RequiredArgsConstructor
public class JoinWaitUsernameCommand implements WorkflowCommand {

    private final WorkflowRepository workflowRepository;

    @Override
    public void execute(UpdateData data, TelegramMessageSender sender) {
        var chatId = data.chatId();
        var username = data.messageText();
        log.info("Пользователь {} ввел имя ползователя", chatId);
        var flow = workflowRepository.findByChatId(chatId).orElse(new UserWorkflow(chatId));
        flow.getUserRegistrationDto().setUsername(username);

        //TODO Сохранение пользователя в БД

        sender.send(MessageBuilder.greetingsText(chatId, username));
        sender.send(MessageBuilder.mainMenu(chatId));

        flow.setCurrentStep(WorkflowStep.JOIN_COMPLETED);
        workflowRepository.saveFlow(flow);
    }

    @Override
    public WorkflowStep getInitStep() {
        return WorkflowStep.JOIN_WAIT_FOR_NAME;
    }
}
