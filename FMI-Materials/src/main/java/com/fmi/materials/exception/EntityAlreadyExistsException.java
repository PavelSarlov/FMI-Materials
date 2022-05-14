package com.fmi.materials.exception;

public class EntityAlreadyExistsException extends RuntimeException {

    public EntityAlreadyExistsException()  {
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

