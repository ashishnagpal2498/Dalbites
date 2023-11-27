package com.asdc.dalbites.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception indicating a conflict due to a duplicate entry.
 * This exception is annotated with {@link ResponseStatus} to set the HTTP
 * response status code to {@link HttpStatus#CONFLICT} (409 - Conflict).
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class DuplicateEntryException extends Exception{

    /**
     * Constructs a {@code DuplicateEntryException} with the specified detail message
     * and a wrapped exception.
     *
     * @param message a description of the exception
     * @param e the wrapped exception that caused or is related to this exception
     */
    public DuplicateEntryException(String message, Exception e){
        super(message, e);
    }
}
