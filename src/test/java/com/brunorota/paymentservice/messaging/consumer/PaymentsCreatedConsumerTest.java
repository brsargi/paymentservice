package com.brunorota.paymentservice.messaging.consumer;

import com.brunorota.paymentservice.model.Payment;
import com.brunorota.paymentservice.service.WebhookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class PaymentsCreatedConsumerTest {

    @Mock
    private WebhookService webhookService;

    private PaymentsCreatedConsumer paymentsCreatedConsumer;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        paymentsCreatedConsumer = new PaymentsCreatedConsumer(webhookService);

        this.objectMapper = new ObjectMapper();
    }

    @Test
    void receivePaymentCreated_shouldCallWebhookService() {

        String paymentMessage = createPayload();

        paymentsCreatedConsumer.receivePaymentCreated(paymentMessage);

        verify(webhookService, times(1)).callWebhooks(paymentMessage);
    }

    private String createPayload() {

        final Payment payment = new Payment();
        payment.setId(1L);
        payment.setFirstName("FirstName");
        payment.setLastName("LastName");
        payment.setCardNumber("CardNumber");

        String payload = null;

        try {
            payload = objectMapper.writeValueAsString(payment);
        } catch (JsonProcessingException e) {
        }

        return payload;
    }
}
