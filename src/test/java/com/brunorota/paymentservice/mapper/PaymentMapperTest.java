package com.brunorota.paymentservice.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.brunorota.paymentservice.dto.PaymentDto;
import com.brunorota.paymentservice.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PaymentMapperTest {

    private PaymentMapper paymentMapper;

    @BeforeEach
    void setUp() {
        paymentMapper = new PaymentMapper();
    }

    @Test
    void toEntity_shouldMapDtoToEntityCorrectly() {

        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setFirstName("Bruno");
        paymentDto.setLastName("Rota");
        paymentDto.setZipCode("12345");
        paymentDto.setCardNumber("1234-5678-9876-5432");

        Payment payment = paymentMapper.toEntity(paymentDto);

        assertNotNull(payment);
        assertEquals(paymentDto.getFirstName(), payment.getFirstName());
        assertEquals(paymentDto.getLastName(), payment.getLastName());
        assertEquals(paymentDto.getZipCode(), payment.getZipCode());
        assertEquals(paymentDto.getCardNumber(), payment.getCardNumber());
    }

    @Test
    void toEntity_shouldReturnNullIfDtoIsNull() {
        Payment payment = paymentMapper.toEntity(null);

        assertNull(payment);
    }
}
