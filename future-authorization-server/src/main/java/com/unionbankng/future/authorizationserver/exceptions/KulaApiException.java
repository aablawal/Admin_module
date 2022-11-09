package com.unionbankng.future.authorizationserver.exceptions;

import org.springframework.http.HttpStatus;

public class KulaApiException extends RuntimeException {

    private HttpStatus status;
    private String message;

    public KulaApiException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public KulaApiException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}