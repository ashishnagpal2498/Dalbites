package com.asdc.dalbites.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class InvalidCredentialException extends Exception{
    /**
     * Constructs an {@code InvalidCredentialException} with the specified detail message
     * and a wrapped exception.
     *
     * @param message a description of the exception
     * @param e the wrapped exception that caused or is related to this exception
     */
    public InvalidCredentialException(String message, Exception e){
        super(message, e);
    }
}
