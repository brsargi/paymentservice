package com.brunorota.paymentservice.exceptions;

public class WebhookCallbackException extends RuntimeException{

    public WebhookCallbackException(final String message) {
        super(message);
    }

    public WebhookCallbackException(final String message, final Exception e) {
        super(message, e);
    }
}
