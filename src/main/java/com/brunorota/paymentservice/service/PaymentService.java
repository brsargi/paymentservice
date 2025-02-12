package com.brunorota.paymentservice.service;

import com.brunorota.paymentservice.messaging.producer.PaymentsCreatedProducer;
import com.brunorota.paymentservice.model.Payment;
import com.brunorota.paymentservice.repository.PaymentRepository;
import com.brunorota.paymentservice.util.EncryptionUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

    private final PaymentRepository paymentRepository;

    private final PaymentsCreatedProducer paymentsCreatedProducer;

    private final EncryptionUtil encryptionUtil;

    private final ObjectMapper objectMapper;

    @Autowired
    public PaymentService(final PaymentRepository paymentRepository,
                          final PaymentsCreatedProducer paymentsCreatedProducer,
                          final EncryptionUtil encryptionUtil,
                          final ObjectMapper objectMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentsCreatedProducer = paymentsCreatedProducer;
        this.encryptionUtil = encryptionUtil;
        this.objectMapper = objectMapper;
    }

    public Payment savePayment(Payment payment) {

        payment.setCardNumber(encryptionUtil.encrypt(payment.getCardNumber()));

        final Payment paymentCreated = paymentRepository.save(payment);

        paymentsCreatedProducer.sendPaymentMessage(convertPaymentToJson(payment));

        return paymentCreated;
    }

    public String convertPaymentToJson(Payment paymentCreated) {
        try {
            return objectMapper.writeValueAsString(paymentCreated);
        } catch (final JsonProcessingException e) {
            log.error("Fail to convert the payment with id {} to json", paymentCreated.getId());
            return null;
        }
    }
}
