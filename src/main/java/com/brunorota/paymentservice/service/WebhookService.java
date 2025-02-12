package com.brunorota.paymentservice.service;

import com.brunorota.paymentservice.exceptions.WebhookCallbackException;
import com.brunorota.paymentservice.model.Webhook;
import com.brunorota.paymentservice.repository.WebhookRepository;
import com.brunorota.paymentservice.util.EncryptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.util.List;

@Service
public class WebhookService {

    private static final Logger log = LoggerFactory.getLogger(WebhookService.class);

    private final WebhookRepository webhookRepository;

    private final RestTemplate restTemplate;

    @Autowired
    public WebhookService(final WebhookRepository webhookRepository, final RestTemplate restTemplate) {
        this.webhookRepository = webhookRepository;
        this.restTemplate = restTemplate;
    }

    public Webhook saveWebhook(Webhook webhook) {
        return webhookRepository.save(webhook);
    }

    private List<Webhook> getAllWebhooks() {
        return webhookRepository.findAll();
    }

    public void callWebhooks(final String payload) {
        for (final Webhook webhook : getAllWebhooks()) {
            sendPostRequest(webhook.getEndpoint(), payload);
        }
    }

    private void sendPostRequest(String url, String payload) {
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        final HttpEntity<String> entity = new HttpEntity<>(payload, headers);

        final ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {

            log.error("Fail to call the webhook. ResponseStatusCode={}", response.getStatusCode().value());

            throw new WebhookCallbackException("Fail to call the webhook. ResponseStatusCode=" +
                    response.getStatusCode().value());
        }
    }
}
