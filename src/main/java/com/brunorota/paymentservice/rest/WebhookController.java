package com.brunorota.paymentservice.rest;

import com.brunorota.paymentservice.dto.WebhookDto;
import com.brunorota.paymentservice.mapper.WebhookMapper;
import com.brunorota.paymentservice.model.Webhook;
import com.brunorota.paymentservice.service.WebhookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/webhook")
public class WebhookController {

    private final WebhookService webhookService;

    private final WebhookMapper webhookMapper;

    @Autowired
    public WebhookController(final WebhookService webhookService, final WebhookMapper webhookMapper) {
        this.webhookService = webhookService;
        this.webhookMapper = webhookMapper;
    }

    @Operation(
            summary = "Save a new webhook event",
            description = "This endpoint allows you to save a new webhook event in the system using the data provided in the WebhookDto.",
            tags = {"Webhook"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Webhook successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid request, incorrect or missing data")
    })
    @PostMapping
    public ResponseEntity<String> saveWebhook(@Valid @RequestBody WebhookDto webhookDto) {

        webhookService.saveWebhook(webhookMapper.toEntity(webhookDto));

        return ResponseEntity.status(HttpStatus.CREATED).body("Webhook created successfully");
    }
}
