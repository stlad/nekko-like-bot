package ru.vaganov.nekkolike.business.process.workflow.command.mycats;

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

@Slf4j
@Component
@RequiredArgsConstructor
public class MyCatsInfoReceivedCommand implements WorkflowCommand {
    private final WorkflowRepository workflowRepository;

    @Override
    public void execute(UpdateData data, TelegramMessageSender sender) {
        var chatId = data.chatId();
        log.info("Пользователь {} получил информацию о котике", chatId);
        var flow = workflowRepository.findByChatId(chatId).orElseThrow(() -> new WorkflowNotFoundException(chatId));

        sender.send(MessageBuilder.catInfoMenu(chatId, flow.getCatInfoDto().getAuthorTelegramUsername(),
                flow.getCatInfoDto().getCatName(), flow.getCatInfoDto().getCatId(), flow.getCatInfoDto().getPhoto()));

    }

    @Override
    public WorkflowStep getInitStep() {
        return WorkflowStep.MY_CATS_INFO_RECEIVED;
    }
}
