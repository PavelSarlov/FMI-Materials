package com.fmi.materials.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EntityNotFoundException extends CustomException {

    protected HttpStatus status = HttpStatus.NOT_FOUND;

    public EntityNotFoundException()  {
        super();
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    } 

    public EntityNotFoundException(String message) {
        super(message);
    } 

    public EntityNotFoundException(Throwable cause) {
        super(cause);
    } 
}
