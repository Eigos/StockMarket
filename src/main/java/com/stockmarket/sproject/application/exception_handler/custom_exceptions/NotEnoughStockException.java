package com.stockmarket.sproject.application.exception_handler.custom_exceptions;

public class NotEnoughStockException extends MessageException{

    public NotEnoughStockException(String message) {
        super(message);
    }
    
}
