package com.brunorota.paymentservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String PAYMENTS_EXCHANGE = "payments";
    public static final String PAYMENTS_ROUTING_KEY = "payments.created";
    public static final String PAYMENTS_QUEUE = "payment.created";
    public static final String PAYMENTS_DLQ = "payment.created.dlq";
    public static final String PAYMENTS_DLX = "payments.dlx";
    private static final int MAX_RETRIES = 3;
    private static final int RETRY_DELAY_MS = 5000;

    @Bean
    Queue paymentsQueue() {
        return QueueBuilder.durable(PAYMENTS_QUEUE)
                .withArgument("x-dead-letter-exchange", PAYMENTS_DLX)
                .withArgument("x-dead-letter-routing-key", PAYMENTS_ROUTING_KEY)
                .withArgument("x-max-retries", MAX_RETRIES)
                .withArgument("x-message-ttl", RETRY_DELAY_MS)
                .build();
    }

    @Bean
    TopicExchange paymentsExchange() {
        return new TopicExchange(PAYMENTS_EXCHANGE);
    }

    @Bean
    Binding paymentsBinding(Queue paymentsQueue, TopicExchange paymentsExchange) {
        return BindingBuilder.bind(paymentsQueue)
                .to(paymentsExchange)
                .with(PAYMENTS_ROUTING_KEY);
    }

    @Bean
    Queue paymentsDLQ() {
        return QueueBuilder.durable(PAYMENTS_DLQ).build();
    }

    @Bean
    TopicExchange paymentsDLX() {
        return new TopicExchange(PAYMENTS_DLX);
    }

    @Bean
    Binding paymentsDLQBinding(Queue paymentsDLQ, TopicExchange paymentsDLX) {
        return BindingBuilder.bind(paymentsDLQ)
                .to(paymentsDLX)
                .with(PAYMENTS_ROUTING_KEY);
    }
}