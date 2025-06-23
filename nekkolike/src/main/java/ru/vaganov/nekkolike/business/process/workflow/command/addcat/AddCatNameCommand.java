package ru.vaganov.nekkolike.business.process.workflow.command.addcat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
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
public class AddCatNameCommand implements WorkflowCommand {
    private final WorkflowRepository workflowRepository;

    @Override
    public void execute(UpdateData data, TelegramMessageSender sender) {
        var chatId = data.chatId();
        var catName = data.messageText();
        var telegramUserName = data.telegramUsername();
        log.info("Пользователь {} прислал имя котика {}", chatId, catName);
        var flow = workflowRepository.findByChatId(chatId).orElseThrow(() -> new WorkflowNotFoundException(chatId));

        flow.getCatRegistrationDto().setCatName(catName);

        sender.send(MessageBuilder.catPhoto(chatId, flow.getCatRegistrationDto().getPhoto()));
        sender.send(MessageBuilder.acceptCatName(chatId, telegramUserName, catName, flow.getCatRegistrationDto().getCatId()));

        flow.setCurrentStep(WorkflowStep.ADD_CAT_WAIT_FOR_ACCEPT);
        workflowRepository.saveFlow(flow);
    }

    @Override
    public WorkflowStep getInitStep() {
        return WorkflowStep.ADD_CAT_WAIT_FOR_NAME;
    }
}
