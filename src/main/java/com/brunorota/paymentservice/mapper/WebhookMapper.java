package com.brunorota.paymentservice.mapper;

import com.brunorota.paymentservice.dto.WebhookDto;
import com.brunorota.paymentservice.model.Webhook;
import org.springframework.stereotype.Component;

@Component
public class WebhookMapper {

    public Webhook toEntity(WebhookDto webhookDto) {

        if (webhookDto == null) {
            return null;
        }

        final Webhook webhook = new Webhook();
        webhook.setEndpoint(webhookDto.getEndpoint());

        return webhook;
    }
}
