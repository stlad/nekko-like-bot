package ru.vaganov.nekkolike.business.process.workflow.command.mycats;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.bot.exceptions.WorkflowNotFoundException;
import ru.vaganov.nekkolike.bot.response.MessageBuilder;
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
public class MyCatsDeleteCommand implements WorkflowCommand {
    private final WorkflowRepository workflowRepository;
    private final BackendClient backendClient;

    @Override
    public void execute(UpdateData data, TelegramMessageSender sender) {
        var chatId = data.chatId();
        log.info("Пользователь {} запрашивает удаление котика", chatId);
        var flow = workflowRepository.findByChatId(chatId).orElseThrow(() -> new WorkflowNotFoundException(chatId));
        var catId = data.params()[0];

        sender.send(MessageBuilder.deleteCat(chatId));
        backendClient.deleteCat(chatId, UUID.fromString(catId));
    }

    @Override
    public WorkflowStep getInitStep() {
        return WorkflowStep.MY_CATS_DELETE;
    }
}
