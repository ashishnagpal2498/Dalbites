package com.asdc.dalbites.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Exception{
    /**
     * Constructs a {@code ResourceNotFoundException} with the specified detail message.
     *
     * @param message a description of the not found condition
     */

    public ResourceNotFoundException(String message){super(message);}
}
