package com.fmi.materials.dto.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseDtoError<T> extends ResponseDto {

    private T error;

    public ResponseDtoError(HttpStatus status, T error) {
        super(status);
        this.error = error;
    }
}
