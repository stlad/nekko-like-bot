package ru.vaganov.nekkolike.business.process.workflow.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.vaganov.nekkolike.bot.NekkoBot;
import ru.vaganov.nekkolike.bot.exceptions.FileProcessingException;
import ru.vaganov.nekkolike.bot.exceptions.WorkflowNotFoundException;
import ru.vaganov.nekkolike.bot.response.MessageBuilder;
import ru.vaganov.nekkolike.business.process.workflow.UserWorkflow;
import ru.vaganov.nekkolike.business.process.workflow.WorkflowStep;
import ru.vaganov.nekkolike.business.process.workflow.repository.WorkflowRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class AddCatFlow {
    private final WorkflowRepository workflowRepository;

    public void addCatStarted(Long chatId, NekkoBot bot) {
        log.info("Пользователь {} начал процесс создания котика", chatId);
        var flow = workflowRepository.findByChatId(chatId).orElse(new UserWorkflow(chatId));
        flow.setCurrentStep(WorkflowStep.ADD_CAT_STARTED);

        flow.initCat();
        bot.send(MessageBuilder.askForCatPhoto(chatId));

        log.info("Ожидается фото котика пользователя от {}", chatId);
        flow.setCurrentStep(WorkflowStep.ADD_CAT_WAIT_FOR_PHOTO);
        workflowRepository.saveFlow(flow);
    }

    public void catPhotoAdded(Long chatId, String photoId, NekkoBot bot) {
        log.info("Пользователь {} прислал фото котика", chatId);
        var flow = workflowRepository.findByChatId(chatId).orElseThrow(() -> new WorkflowNotFoundException(chatId));

        try {
            var telegramFile = bot.execute(new GetFile(photoId));
            var localFile = bot.downloadFile(telegramFile);
            flow.getCatRegistrationDto().setPhoto(localFile);
        } catch (TelegramApiException exception) {
            log.error("Не удалось сохранить файл {}", photoId, exception);
            throw new FileProcessingException("Не удалось сохранить файл " + photoId, exception);
        }

        bot.send(MessageBuilder.askForCatName(chatId));
        log.info("Ожидается имя котика пользователя от {}", chatId);
        flow.setCurrentStep(WorkflowStep.ADD_CAT_WAIT_FOR_NAME);
        workflowRepository.saveFlow(flow);
    }

    public void catNameAdded(Long chatId, String catName, String telegramUserName, NekkoBot bot) {
        log.info("Пользователь {} прислал имя котика {}", chatId, catName);
        var flow = workflowRepository.findByChatId(chatId).orElseThrow(() -> new WorkflowNotFoundException(chatId));

        flow.getCatRegistrationDto().setCatName(catName);

        bot.send(MessageBuilder.catAcceptPhoto(chatId, flow.getCatRegistrationDto().getPhoto()));
        bot.send(MessageBuilder.catAcceptName(chatId, telegramUserName, catName));

        flow.setCurrentStep(WorkflowStep.ADD_CAT_WAIT_FOR_ACCEPT);
        workflowRepository.saveFlow(flow);
    }

    public void catAccepted(Long chatId, NekkoBot bot) {
        log.info("Пользователь {} подтвердил создание котика", chatId);
        var flow = workflowRepository.findByChatId(chatId).orElseThrow(() -> new WorkflowNotFoundException(chatId));

        bot.send(MessageBuilder.catCreated(chatId));
        bot.send(MessageBuilder.mainMenu(chatId));

        //TODO Сохранение котика в сервис

        flow.setCurrentStep(WorkflowStep.ADD_CAT_COMPLETED);
        workflowRepository.saveFlow(flow);
    }
}
