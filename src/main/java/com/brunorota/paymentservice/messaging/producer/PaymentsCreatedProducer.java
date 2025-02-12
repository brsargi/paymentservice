package com.brunorota.paymentservice.messaging.producer;

import com.brunorota.paymentservice.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentsCreatedProducer {
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public PaymentsCreatedProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendPaymentMessage(String message) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.PAYMENTS_EXCHANGE, RabbitMQConfig.PAYMENTS_ROUTING_KEY, message);
    }
}
