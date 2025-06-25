package ru.vaganov.nekkolike.business.process.workflow.command.showcat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.bot.response.TelegramMessageSender;
import ru.vaganov.nekkolike.bot.utils.UpdateData;
import ru.vaganov.nekkolike.business.process.workflow.UserWorkflow;
import ru.vaganov.nekkolike.business.process.workflow.WorkflowStep;
import ru.vaganov.nekkolike.business.process.workflow.backend.BackendClient;
import ru.vaganov.nekkolike.business.process.workflow.command.WorkflowCommand;
import ru.vaganov.nekkolike.business.process.workflow.repository.WorkflowRepository;

@Component
@Slf4j
@RequiredArgsConstructor
public class ShowCatStartCommand implements WorkflowCommand {
    private final BackendClient backendClient;
    private final WorkflowRepository workflowRepository;

    @Override
    public void execute(UpdateData data, TelegramMessageSender sender) {
        var chatId = data.chatId();
        log.info("Пользователь {} запрашивает котика для оценки", chatId);
        var flow = workflowRepository.findByChatId(chatId).orElse(new UserWorkflow(chatId));

        backendClient.requestRandomCat(chatId);
        flow.setCurrentStep(WorkflowStep.SHOW_CAT_RECEIVED);
    }

    @Override
    public WorkflowStep getInitStep() {
        return WorkflowStep.SHOW_CAT_STARTED;
    }
}
