package ru.vaganov.nekkolike.business.process.workflow.command.addcat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.vaganov.nekkolike.bot.exceptions.FileProcessingException;
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
public class AddCatPhotoCommand implements WorkflowCommand {
    private final WorkflowRepository workflowRepository;

    @Override
    public void execute(UpdateData data, TelegramMessageSender sender) {
        var chatId = data.chatId();
        var photo = data.photo();
        log.info("Пользователь {} прислал фото котика", chatId);
        var flow = workflowRepository.findByChatId(chatId).orElseThrow(() -> new WorkflowNotFoundException(chatId));

        flow.getCatRegistrationDto().setPhoto(photo);
        sender.send(MessageBuilder.askForCatName(chatId));

        log.info("Ожидается имя котика пользователя от {}", chatId);
        flow.setCurrentStep(WorkflowStep.ADD_CAT_WAIT_FOR_NAME);
        workflowRepository.saveFlow(flow);
    }

    @Override
    public WorkflowStep getInitStep() {
        return WorkflowStep.ADD_CAT_WAIT_FOR_PHOTO;
    }
}
