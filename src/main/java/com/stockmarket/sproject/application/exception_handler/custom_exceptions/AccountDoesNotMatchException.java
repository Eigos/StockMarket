package com.stockmarket.sproject.application.exception_handler.custom_exceptions;

public class AccountDoesNotMatchException extends MessageException {

    public AccountDoesNotMatchException(String message) {
        super(message);
    }
    
}
