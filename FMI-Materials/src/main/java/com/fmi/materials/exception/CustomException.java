 package com.fmi.materials.exception;

 import lombok.Getter;
 import org.springframework.http.HttpStatus;

 @Getter
 public class CustomException extends RuntimeException {

    protected HttpStatus status = HttpStatus.BAD_REQUEST;

    public CustomException()  {
        super();
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    } 

    public CustomException(String message) {
        super(message);
    } 

    public CustomException(Throwable cause) {
        super(cause);
    }
}


