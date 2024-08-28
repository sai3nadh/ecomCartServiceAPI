package com.uh.herts.CartServiceAPI.exception;

import org.springframework.http.HttpStatus;

public class ProductAPIException extends RuntimeException{
    private HttpStatus status;
    private String message;

    public ProductAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public ProductAPIException(String message, HttpStatus status, String message1) {
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
