package ru.vaganov.nekkolike.business.process.workflow.command.mycats;

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
import ru.vaganov.nekkolike.common.dto.CatListDto;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyCatsViewPageCommand implements WorkflowCommand {
    private final WorkflowRepository workflowRepository;
    private final BackendClient backendClient;

    @Override
    public void execute(UpdateData data, TelegramMessageSender sender) {
        var chatId = data.chatId();
        log.info("Пользователь {} начал процесс просмотра своих котиков", chatId);
        var flow = workflowRepository.findByChatId(chatId).orElse(new UserWorkflow(chatId));

        if (data.params().length == 0) {
            backendClient.getNextCatPage(chatId, new CatListDto());
        } else if ("NEXT".equals(data.params()[0])) {
            backendClient.getNextCatPage(chatId, flow.getCatListDto());
        } else {
            backendClient.getPrevCatPage(chatId, flow.getCatListDto());
        }

        flow.setCurrentStep(WorkflowStep.MY_CATS_VIEW_PAGE_RECEIVED);
        workflowRepository.saveFlow(flow);
    }

    @Override
    public WorkflowStep getInitStep() {
        return WorkflowStep.MY_CATS_VIEW_PAGE;
    }
}