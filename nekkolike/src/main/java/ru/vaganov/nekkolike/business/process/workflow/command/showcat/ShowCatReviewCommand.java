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

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class ShowCatReviewCommand implements WorkflowCommand {
    private final WorkflowRepository workflowRepository;
    private final BackendClient backendClient;

    @Override
    public void execute(UpdateData data, TelegramMessageSender sender) {
        var chatId = data.chatId();
        log.info("Пользователь {} лайкнул котика", chatId);
        var flow = workflowRepository.findByChatId(chatId).orElse(new UserWorkflow(chatId));
        var isLike = "LIKE".equals(data.params()[1]);
        var catId = UUID.fromString(data.params()[2]);
        if (isLike) {
            backendClient.likeCat(chatId, catId);
        } else {
            backendClient.dislikeCat(chatId, catId);
        }

        flow.setCurrentStep(WorkflowStep.SHOW_CAT_RECEIVED);
        backendClient.requestRandomCat(chatId);
    }

    @Override
    public WorkflowStep getInitStep() {
        return WorkflowStep.SHOW_CAT_REVIEW;
    }
}
