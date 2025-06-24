package ru.vaganov.nekkolike.bot.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {
    @Value("${spring.rabbitmq.queue_request.name}")
    private String queueRequestName;
    @Value("${spring.rabbitmq.queue_response.name}")
    private String queueResponseName;
    @Value("${spring.rabbitmq.exchange.name}")
    private String exchangeName;
    @Value("${spring.rabbitmq.routing.request_key}")
    private String routingRequestKey;
    @Value("${spring.rabbitmq.routing.response_key}")
    private String routingResponseKey;

    @Bean
    public Queue requestQueue() {
        return new Queue(queueRequestName, false);
    }

    @Bean
    public Queue responseQueue() {
        return new Queue(queueResponseName, false);
    }

    @Bean
    public Exchange exchange() {
        return new DirectExchange(exchangeName, false, false);
    }

    @Bean
    public Binding requestBinding(Queue requestQueue, DirectExchange exchange) {
        return BindingBuilder.bind( requestQueue)
                .to(exchange)
                .with(routingRequestKey);
    }
    @Bean
    public Binding responseBinding(Queue responseQueue, DirectExchange exchange) {
        return BindingBuilder.bind( responseQueue)
                .to(exchange)
                .with(routingRequestKey);
    }

}