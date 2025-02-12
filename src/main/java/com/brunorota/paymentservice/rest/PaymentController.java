package com.brunorota.paymentservice.rest;

import com.brunorota.paymentservice.dto.PaymentDto;
import com.brunorota.paymentservice.mapper.PaymentMapper;
import com.brunorota.paymentservice.model.Payment;
import com.brunorota.paymentservice.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentMapper paymentMapper;

    public PaymentController(PaymentService paymentService, PaymentMapper paymentMapper) {
        this.paymentService = paymentService;
        this.paymentMapper = paymentMapper;
    }

    @Operation(
            summary = "Create a new payment",
            description = "This endpoint allows you to create a new payment in the system using the data provided in the PaymentDto.",
            tags = {"Payments"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Payment successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid request, incorrect or missing data")
    })
    @PostMapping
    public ResponseEntity<String> createPayment(@Valid @RequestBody PaymentDto paymentDto) {

        Payment payment = paymentMapper.toEntity(paymentDto);
        paymentService.savePayment(payment);

        return ResponseEntity.status(HttpStatus.CREATED).body("Payment created successfully");
    }
}
