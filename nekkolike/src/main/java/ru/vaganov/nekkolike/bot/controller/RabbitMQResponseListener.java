package ru.vaganov.nekkolike.bot.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.bot.commands.BotCommand;
import ru.vaganov.nekkolike.bot.exceptions.CommandProcessingFailedException;
import ru.vaganov.nekkolike.bot.exceptions.WorkflowNotFoundException;
import ru.vaganov.nekkolike.bot.response.TelegramMessageSender;
import ru.vaganov.nekkolike.bot.service.CommandExecutor;
import ru.vaganov.nekkolike.bot.utils.TelegramBotUtils;
import ru.vaganov.nekkolike.bot.utils.UpdateData;
import ru.vaganov.nekkolike.business.process.workflow.UserWorkflow;
import ru.vaganov.nekkolike.business.process.workflow.dto.CatRegistrationDto;
import ru.vaganov.nekkolike.business.process.workflow.repository.WorkflowRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMQResponseListener {

    private final CommandExecutor commandExecutor;
    private final TelegramMessageSender telegramMessageSender;
    private final WorkflowRepository workflowRepository;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "${spring.rabbitmq.queue_response.name}")
    public void onMessage(String message) {
        RabbitResponseDto dto = null;
        log.info("onMessage {}", message);
        try {
            dto = objectMapper.readValue(message, RabbitResponseDto.class);
        } catch (JsonProcessingException e) {
            throw new CommandProcessingFailedException();
        }
        var updateData = TelegramBotUtils.createUpdateData(dto);
        var flow = workflowRepository.findByChatId(updateData.chatId())
                .orElse(new UserWorkflow(updateData.chatId()));

//        TODO Пример обработки команд
//        if(dto.getCatReview() != null && SHOW_CATS_REVIEW.equals(flow.getCurrentStep())){
//            flow.setCatReviewDto(dto.getCatReview());
//            workflowRepository.saveFlow(flow);
//            commandExecutor.executeCommand(WorkflowStep.SHOW_CAT_RECEIVED, updateData, telegramMessageSender);
//        }
//        else{
//            throw new CommandProcessingFailedException();
//        }
//        Для других команд.......
    }

}
