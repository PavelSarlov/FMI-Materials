package com.fmi.materials.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidArgumentException extends CustomException {

    protected HttpStatus status = HttpStatus.BAD_REQUEST;

    public InvalidArgumentException() {
        super();
    }

    public InvalidArgumentException(String message, Throwable cause) {
        super(message, cause);
    } 

    public InvalidArgumentException(String message) {
        super(message);
    } 

    public InvalidArgumentException(Throwable cause) {
        super(cause);
    } 
}
