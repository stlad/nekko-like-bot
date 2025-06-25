package ru.vaganov.nekkolike.business.process.workflow.command.showcat;

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
public class ShowCatRecievedCommand implements WorkflowCommand {
    private final WorkflowRepository workflowRepository;

    @Override
    public void execute(UpdateData data, TelegramMessageSender sender) {
        var chatId = data.chatId();
        log.info("Пользователь {} получил котика для оценки", chatId);
        var flow = workflowRepository.findByChatId(chatId).orElse(new UserWorkflow(chatId));

        var catDto = flow.getCatReviewDto();

        sender.send(MessageBuilder.likeCatMenu(chatId, catDto.getAuthorTelegramUsername(), catDto.getCatName(),
                catDto.getCatId(), catDto.getPhoto(), catDto.getLikeCount(), catDto.getDislikeCount()));
    }

    @Override
    public WorkflowStep getInitStep() {
        return WorkflowStep.SHOW_CAT_RECEIVED;
    }
}
