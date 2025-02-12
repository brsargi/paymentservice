package com.brunorota.paymentservice.rest;

import com.brunorota.paymentservice.dto.PaymentDto;
import com.brunorota.paymentservice.mapper.PaymentMapper;
import com.brunorota.paymentservice.model.Payment;
import com.brunorota.paymentservice.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PaymentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PaymentService paymentService;

    @Mock
    private PaymentMapper paymentMapper;

    @InjectMocks
    private PaymentController paymentController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();
    }

    @Test
    void createPayment_shouldReturn201_whenPaymentIsValid() throws Exception {

        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setFirstName("Bruno");
        paymentDto.setLastName("Sargi");
        paymentDto.setZipCode("12345");
        paymentDto.setCardNumber("1234-5678-9876-5432");

        Payment payment = new Payment();
        when(paymentMapper.toEntity(paymentDto)).thenReturn(payment);
        when(paymentService.savePayment(payment)).thenReturn(payment);

        mockMvc.perform(post("/api/v1/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Payment created successfully"));
    }

    @Test
    void createPayment_shouldReturn400_whenPaymentIsInvalid() throws Exception {

        PaymentDto invalidPaymentDto = new PaymentDto();
        invalidPaymentDto.setLastName("Sargi");
        invalidPaymentDto.setZipCode("12345");
        invalidPaymentDto.setCardNumber("1234-5678-9876-5432");

        mockMvc.perform(post("/api/v1/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidPaymentDto)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(paymentMapper);
        verifyNoInteractions(paymentService);
    }
}
