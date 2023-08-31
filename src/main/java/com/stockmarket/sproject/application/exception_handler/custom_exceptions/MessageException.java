package com.stockmarket.sproject.application.exception_handler.custom_exceptions;

public class MessageException extends RuntimeException {
    
    public MessageException(String message){
        super(message);
    }

}
