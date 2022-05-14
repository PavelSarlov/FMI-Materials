package com.fmi.materials.exception;

public class EntityNotFoundException extends CustomException {

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
