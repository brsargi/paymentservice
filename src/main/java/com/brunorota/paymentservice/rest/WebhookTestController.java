package com.brunorota.paymentservice.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Test controller to receive the request for the webhook notification.
 */
@RestController
@RequestMapping("/testwebhook")
public class WebhookTestController {

    private static final Logger log = LoggerFactory.getLogger(WebhookTestController.class);

    @PostMapping
    public void receiveWebhook(@RequestBody String payload) {

        log.info(payload);
    }
}
