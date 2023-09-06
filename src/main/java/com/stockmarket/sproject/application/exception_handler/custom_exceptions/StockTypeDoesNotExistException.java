package com.stockmarket.sproject.application.exception_handler.custom_exceptions;

public class StockTypeDoesNotExistException extends MessageException {

    public StockTypeDoesNotExistException(String message) {
        super(message);
    }
    
}
