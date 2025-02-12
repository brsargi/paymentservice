package com.brunorota.paymentservice.service;

import com.brunorota.paymentservice.exceptions.WebhookCallbackException;
import com.brunorota.paymentservice.model.Webhook;
import com.brunorota.paymentservice.repository.WebhookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WebhookServiceTest {

    @Mock
    private WebhookRepository webhookRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WebhookService webhookService;

    private Webhook webhook;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        webhook = new Webhook();
        webhook.setId(1L);
        webhook.setEndpoint("http://brunorota.com/webhook");
    }

    @Test
    void saveWebhook_shouldSaveWebhook() {

        when(webhookRepository.save(webhook)).thenReturn(webhook);

        Webhook savedWebhook = webhookService.saveWebhook(webhook);

        assertNotNull(savedWebhook);
        assertEquals(webhook.getId(), savedWebhook.getId());
        verify(webhookRepository, times(1)).save(webhook);
    }

    @Test
    void callWebhooks_shouldCallAllWebhooks() {

        when(webhookRepository.findAll()).thenReturn(List.of(webhook, webhook, webhook));
        ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(restTemplate.postForEntity(eq(webhook.getEndpoint()), any(), eq(String.class)))
                .thenReturn(responseEntity);

        webhookService.callWebhooks("PayloadTest");

        verify(restTemplate, times(3)).postForEntity(eq(webhook.getEndpoint()), any(), eq(String.class));
    }

    @Test
    void callWebhooks_shouldThrowExceptionWhenWebhookFails() {

        when(webhookRepository.findAll()).thenReturn(List.of(webhook));
        ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        when(restTemplate.postForEntity(eq(webhook.getEndpoint()), any(), eq(String.class)))
                .thenReturn(responseEntity);

        WebhookCallbackException exception = assertThrows(WebhookCallbackException.class, () -> {
            webhookService.callWebhooks("PayloadTest");
        });

        assertEquals("Fail to call the webhook. ResponseStatusCode=500", exception.getMessage());
    }
}