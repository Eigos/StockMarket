package com.stockmarket.sproject.application.exception_handler.custom_exceptions;

import com.stockmarket.sproject.application.exception_handler.EntityNotFoundException;
import com.stockmarket.sproject.application.model.Account;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException(int id) {
        super(Account.class, "id", Integer.toString(id));
    }

    public UserNotFoundException(String email){
        super(Account.class, "email", email);
    }

}
