package ru.vaganov.nekkolike.bot.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.bot.commands.BotCommand;
import ru.vaganov.nekkolike.bot.exceptions.CommandProcessingFailedException;
import ru.vaganov.nekkolike.bot.response.MessageBuilder;
import ru.vaganov.nekkolike.bot.response.TelegramMessageSender;
import ru.vaganov.nekkolike.bot.service.CommandExecutor;
import ru.vaganov.nekkolike.bot.utils.TelegramBotUtils;
import ru.vaganov.nekkolike.business.process.workflow.UserWorkflow;
import ru.vaganov.nekkolike.business.process.workflow.repository.WorkflowRepository;
import ru.vaganov.nekkolike.common.dto.RabbitResponseDto;

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


        switch (dto.getAction()) {
            case GET_RANDOM_CAT_RESPONSE -> {
                flow.setCatInfoDto(dto.getCatInfoDto());
                workflowRepository.saveFlow(flow);
                commandExecutor.executeCommand(BotCommand.SHOW_CAT_RECEIVED, updateData, telegramMessageSender);
            }
            case GET_CONCRETE_CAT_RESPONSE -> {
                flow.setCatInfoDto(dto.getCatInfoDto());
                workflowRepository.saveFlow(flow);
                commandExecutor.executeCommand(BotCommand.MY_CATS_INFO_RECEIVED, updateData, telegramMessageSender);
            }
            case GET_CAT_LIST_RESPONSE -> {
                flow.setCatListDto(dto.getCatListDto());
                workflowRepository.saveFlow(flow);
                commandExecutor.executeCommand(BotCommand.MY_CATS_PAGE_RECEIVED, updateData, telegramMessageSender);
            }
            case ERROR -> {
                log.error("На сервере произошла ошибка: {}", dto.getErrorText());
                telegramMessageSender.send(
                        MessageBuilder.errorResponse(dto.getChatId(), "На сервере произошла ошибка")
                );
            }
        }
    }

}
