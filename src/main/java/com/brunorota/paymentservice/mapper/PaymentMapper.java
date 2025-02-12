package com.brunorota.paymentservice.mapper;

import com.brunorota.paymentservice.dto.PaymentDto;
import com.brunorota.paymentservice.model.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public Payment toEntity(PaymentDto paymentDto) {

        if (paymentDto == null) {
            return null;
        }

        Payment payment = new Payment();
        payment.setFirstName(paymentDto.getFirstName());
        payment.setLastName(paymentDto.getLastName());
        payment.setZipCode(paymentDto.getZipCode());
        payment.setCardNumber(paymentDto.getCardNumber());

        return payment;
    }
}
