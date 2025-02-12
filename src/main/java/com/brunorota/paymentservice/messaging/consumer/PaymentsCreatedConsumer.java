package com.brunorota.paymentservice.messaging.consumer;

import com.brunorota.paymentservice.config.RabbitMQConfig;
import com.brunorota.paymentservice.service.WebhookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentsCreatedConsumer {

    private static final Logger log = LoggerFactory.getLogger(PaymentsCreatedConsumer.class);

    private final WebhookService webhookService;

    @Autowired
    public PaymentsCreatedConsumer(final WebhookService webhookService) {
        this.webhookService = webhookService;
    }

    @RabbitListener(queues = RabbitMQConfig.PAYMENTS_QUEUE)
    public void receivePaymentCreated(final String message) {

        log.info("Payment created. Payload={}", message);

        webhookService.callWebhooks(message);
    }
}
