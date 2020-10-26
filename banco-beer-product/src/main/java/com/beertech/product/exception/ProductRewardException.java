package com.beertech.product.exception;

import org.springframework.http.HttpStatus;

public class ProductRewardException extends Exception {

    private HttpStatus httpStatusCode;
    private String message;

    public ProductRewardException(HttpStatus rawStatusCode, String message) {
        this.httpStatusCode = rawStatusCode;
        this.message = message;
    }

    public HttpStatus getHttpStatusCode() {
        return httpStatusCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
