package com.brunorota.paymentservice.messaging.dlq;

import com.brunorota.paymentservice.config.RabbitMQConfig;
import com.brunorota.paymentservice.util.EncryptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentCreatedDlqService {

    private static final Logger log = LoggerFactory.getLogger(PaymentCreatedDlqService.class);

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public PaymentCreatedDlqService(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void reprocessDlqMessages() {
        Message message;
        do {
            message = rabbitTemplate.receive(RabbitMQConfig.PAYMENTS_DLQ);
            if (message != null) {
                final String body = new String(message.getBody());

                log.info("PaymentCreated event reprocessed. Body={}", body);

                rabbitTemplate.convertAndSend(RabbitMQConfig.PAYMENTS_EXCHANGE, RabbitMQConfig.PAYMENTS_ROUTING_KEY, body);
            }
        } while (message != null);
    }
}
