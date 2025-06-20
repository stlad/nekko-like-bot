package ru.vaganov.nekkolike.nekko_service.controller.rabbitmq;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitMQListener {

    @PostConstruct
    public void onPostConstruct() {
        log.info("init consumer");
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.name}")
    public void onMessage(String message) {
        log.info("onMessage {}", message);
    }
}