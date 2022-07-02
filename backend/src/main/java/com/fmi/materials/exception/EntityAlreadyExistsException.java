package com.fmi.materials.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class EntityAlreadyExistsException extends CustomException {

    protected HttpStatus status = HttpStatus.CONFLICT;

    public EntityAlreadyExistsException() {
        super();
    }

    public EntityAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityAlreadyExistsException(String message) {
        super(message);
    }

    public EntityAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
