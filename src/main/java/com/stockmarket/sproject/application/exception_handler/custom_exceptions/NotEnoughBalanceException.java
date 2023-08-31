package com.stockmarket.sproject.application.exception_handler.custom_exceptions;

public class NotEnoughBalanceException extends MessageException{

    public NotEnoughBalanceException(String message) {
        super(message);
    }
    
}
