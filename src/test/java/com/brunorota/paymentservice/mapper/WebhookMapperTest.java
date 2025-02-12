package com.brunorota.paymentservice.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.brunorota.paymentservice.dto.WebhookDto;
import com.brunorota.paymentservice.model.Webhook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WebhookMapperTest {

    private WebhookMapper webhookMapper;

    @BeforeEach
    void setUp() {
        webhookMapper = new WebhookMapper();
    }

    @Test
    void toEntity_shouldMapDtoToEntityCorrectly() {

        WebhookDto webhookDto = new WebhookDto();
        webhookDto.setEndpoint("https://brunorota.com/test");

        Webhook webhook = webhookMapper.toEntity(webhookDto);

        assertNotNull(webhook);
        assertEquals(webhookDto.getEndpoint(), webhook.getEndpoint());
    }

    @Test
    void toEntity_shouldReturnNullIfDtoIsNull() {

        Webhook webhook = webhookMapper.toEntity(null);

        assertNull(webhook);
    }
}
