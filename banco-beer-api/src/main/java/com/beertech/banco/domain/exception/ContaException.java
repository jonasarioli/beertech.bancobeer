package com.beertech.banco.domain.exception;

public class ContaException extends RuntimeException {
    private static final long serialVersionUID = 2371846412124697387L;

    public ContaException(final String message) {
        super(message);
    }

}
