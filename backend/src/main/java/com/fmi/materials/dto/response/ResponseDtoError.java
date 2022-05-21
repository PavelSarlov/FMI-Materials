package com.fmi.materials.dto.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseDtoError extends ResponseDto {

    private String error;

    public ResponseDtoError(HttpStatus status, String error) {
        super(status);
        this.error = error;
    }
}
