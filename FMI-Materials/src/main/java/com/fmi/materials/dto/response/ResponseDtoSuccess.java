package com.fmi.materials.dto.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseDtoSuccess extends ResponseDto {

    private String message;

    public ResponseDtoSuccess(HttpStatus status, String message) {
        super(status);
        this.message = message;
    }
}
