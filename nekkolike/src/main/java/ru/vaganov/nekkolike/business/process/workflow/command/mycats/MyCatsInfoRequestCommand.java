package ru.vaganov.nekkolike.business.process.workflow.command.mycats;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.bot.exceptions.WorkflowNotFoundException;
import ru.vaganov.nekkolike.bot.response.TelegramMessageSender;
import ru.vaganov.nekkolike.bot.utils.UpdateData;
import ru.vaganov.nekkolike.business.process.workflow.WorkflowStep;
import ru.vaganov.nekkolike.business.process.workflow.backend.BackendClient;
import ru.vaganov.nekkolike.business.process.workflow.command.WorkflowCommand;
import ru.vaganov.nekkolike.business.process.workflow.repository.WorkflowRepository;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyCatsInfoRequestCommand implements WorkflowCommand {
    private final WorkflowRepository workflowRepository;
    private final BackendClient backendClient;

    @Override
    public void execute(UpdateData data, TelegramMessageSender sender) {
        var chatId = data.chatId();
        log.info("Пользователь {} запрашивает информацию о котике", chatId);
        var flow = workflowRepository.findByChatId(chatId).orElseThrow(() -> new WorkflowNotFoundException(chatId));
        var catId = data.params()[1];

        backendClient.getCat(chatId, UUID.fromString(catId));

        flow.setCurrentStep(WorkflowStep.MY_CATS_INFO_RECEIVED);
        workflowRepository.saveFlow(flow);
    }

    @Override
    public WorkflowStep getInitStep() {
        return WorkflowStep.MY_CATS_CAT_INFO;
    }
}
