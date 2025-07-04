package ru.vaganov.nekkolike.nekko_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.common.dto.RabbitResponseDto;

@Component
@Slf4j
public class RabbitMQResponseSender {
    private final RabbitTemplate rabbitTemplate;
    private final String exchangeName;
    private final String routingKey;
    private final ObjectMapper objectMapper;

    public RabbitMQResponseSender(
            RabbitTemplate rabbitTemplate,
            @Value("${spring.rabbitmq.exchange.name}") String exchangeName,
            @Value("${spring.rabbitmq.routing.response_key}")String routingKey, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchangeName = exchangeName;
        this.routingKey = routingKey;
        this.objectMapper = objectMapper;
    }

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
