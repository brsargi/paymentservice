package com.brunorota.paymentservice.service;

import com.brunorota.paymentservice.messaging.producer.PaymentsCreatedProducer;
import com.brunorota.paymentservice.model.Payment;
import com.brunorota.paymentservice.repository.PaymentRepository;
import com.brunorota.paymentservice.util.EncryptionUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentsCreatedProducer paymentsCreatedProducer;

    private EncryptionUtil encryptionUtil;

    private ObjectMapper objectMapper;

    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        this.objectMapper = new ObjectMapper();
        this.encryptionUtil = new EncryptionUtil();
        MockitoAnnotations.openMocks(this);
        paymentService = new PaymentService(paymentRepository, paymentsCreatedProducer, encryptionUtil, objectMapper);
    }

    @Test
    void savePayment_shouldSavePaymentAndSendMessage() throws JsonProcessingException {

        final Payment payment = createPayment();

        when(paymentRepository.save(payment)).thenReturn(payment);

        Payment savedPayment = paymentService.savePayment(payment);

        assertNotNull(savedPayment);
        assertEquals("1234-5678-9876-5432", encryptionUtil.decrypt(payment.getCardNumber()));

        verify(paymentRepository, times(1)).save(payment);

        verify(paymentsCreatedProducer, times(1))
                .sendPaymentMessage(paymentService.convertPaymentToJson(payment));
    }

    @Test
    void savePayment_shouldThrowExceptionIfRepositoryThrowException() {

        final Payment payment = createPayment();

        when(paymentRepository.save(payment)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> {
            paymentService.savePayment(payment);
        });
    }

    private Payment createPayment() {
        final Payment payment = new Payment();
        payment.setId(1L);
        payment.setFirstName("Bruno");
        payment.setLastName("Sargi");
        payment.setCardNumber("1234-5678-9876-5432");

        return payment;
    }
}