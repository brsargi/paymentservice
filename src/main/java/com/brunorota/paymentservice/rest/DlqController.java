package com.brunorota.paymentservice.rest;

import com.brunorota.paymentservice.messaging.dlq.PaymentCreatedDlqService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DlqController {

    private final PaymentCreatedDlqService dlqService;

    public DlqController(final PaymentCreatedDlqService dlqService) {
        this.dlqService = dlqService;
    }

    @Operation(
            summary = "Reprocess messages from the Dead Letter Queue",
            description = "This endpoint allows you to reprocess messages from the Dead Letter Queue (DLQ) that have previously failed processing.",
            tags = {"DLQ"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Messages successfully reprocessed"),
            @ApiResponse(responseCode = "500", description = "Internal server error while reprocessing messages")
    })
    @GetMapping("/dlq/reprocess")
    public void listMessages() {
        dlqService.reprocessDlqMessages();
    }
}
