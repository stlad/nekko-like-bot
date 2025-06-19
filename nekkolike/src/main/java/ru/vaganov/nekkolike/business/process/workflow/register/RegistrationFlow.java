package ru.vaganov.nekkolike.business.process.workflow.register;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.bot.NekkoBot;
import ru.vaganov.nekkolike.bot.response.MessageBuilder;
import ru.vaganov.nekkolike.business.process.workflow.UserWorkflow;
import ru.vaganov.nekkolike.business.process.workflow.repository.WorkflowRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class RegistrationFlow {

    private final WorkflowRepository workflowRepository;

    public void joined(Long chatId, NekkoBot bot) {
        log.info("Пользователь {} начал процесс регистрации", chatId);
        var flow = workflowRepository.findByChatId(chatId).orElse(new UserWorkflow(chatId));
        flow.setCurrentStep(RegisterStep.JOINED);

        flow.initRegistration();
        bot.send(MessageBuilder.askForName(chatId));

        log.info("Ожидается имя пользователя от {}", chatId);
        flow.setCurrentStep(RegisterStep.WAIT_FOR_NAME);
        workflowRepository.saveFlow(flow);
    }

    public void waitForUsername(Long chatId, String username, NekkoBot bot) {
        log.info("Пользователь {} ввел имя ползователя", chatId);
        var flow = workflowRepository.findByChatId(chatId).orElse(new UserWorkflow(chatId));
        flow.getUserRegistrationDto().setUsername(username);

        //TODO Сохранение пользователя в БД

        bot.send(MessageBuilder.greetingsText(chatId, username));
        bot.send(MessageBuilder.mainMenu(chatId));

        flow.setCurrentStep(RegisterStep.COMPLETED);
        workflowRepository.saveFlow(flow);
    }
}