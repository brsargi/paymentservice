package com.brunorota.paymentservice.dto;

import jakarta.validation.constraints.NotBlank;

public class WebhookDto {

    @NotBlank(message = "Endpoint is required")
    private String endpoint;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}
