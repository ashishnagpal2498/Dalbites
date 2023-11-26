package com.asdc.dalbites.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class OTPVerificationFailed extends Exception{
    /**
     * Constructs an {@code OTPVerificationFailed} with the specified detail message
     * and a wrapped exception.
     *
     * @param message a description of the exception
     * @param e the wrapped exception that caused or is related to this exception
     */
    public OTPVerificationFailed(String message, Exception e){
        super(message, e);
    }
}
