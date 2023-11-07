package com.asdc.dalbites.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class AccountNotVerifiedException extends Exception{
    public AccountNotVerifiedException(String message, Exception e){
        super(message, e);
    }
}
