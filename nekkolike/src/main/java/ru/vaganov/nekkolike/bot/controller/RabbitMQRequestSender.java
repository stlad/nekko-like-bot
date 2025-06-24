package ru.vaganov.nekkolike.bot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.common.dto.RabbitResponseDto;

@Component
@Slf4j
public class RabbitMQRequestSender {
    private final RabbitTemplate rabbitTemplate;
    private final String exchangeName;
    private final String routingKey;
    private final ObjectMapper objectMapper;

    public RabbitMQRequestSender(
            RabbitTemplate rabbitTemplate,
            @Value("${spring.rabbitmq.exchange.name}") String exchangeName,
            @Value("${spring.rabbitmq.routing.request_key}")String routingKey, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchangeName = exchangeName;
        this.routingKey = routingKey;
        this.objectMapper = objectMapper;
    }

    //TODO Инжектится в backendClient
    public void sendMessage(RabbitResponseDto dto) {
        String message = null;
        try {
            message = objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        log.info(message);
        rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
    }

}
