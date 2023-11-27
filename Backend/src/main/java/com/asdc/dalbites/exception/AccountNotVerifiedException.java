package com.asdc.dalbites.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception indicating that the user account has not been verified.
 * This exception is annotated with {@link ResponseStatus} to set the HTTP
 * response status code to {@link HttpStatus#UNAUTHORIZED} (401 - Unauthorized).
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class AccountNotVerifiedException extends Exception{
    /**
     * Constructs an {@code AccountNotVerifiedException} with the specified detail message
     * and a wrapped exception.
     *
     * @param message a description of the exception
     * @param e the wrapped exception that caused or is related to this exception
     */
    public AccountNotVerifiedException(String message, Exception e){
        super(message, e);
    }
}
