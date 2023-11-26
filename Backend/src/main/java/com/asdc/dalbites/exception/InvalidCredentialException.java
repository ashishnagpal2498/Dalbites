package com.asdc.dalbites.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class InvalidCredentialException extends Exception{
    public InvalidCredentialException(String message, Exception e){
        super(message, e);
    }
}
