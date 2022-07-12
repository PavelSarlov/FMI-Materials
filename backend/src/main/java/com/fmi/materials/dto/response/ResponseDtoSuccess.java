package com.fmi.materials.dto.response;

import org.springframework.http.HttpStatus;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
public class ResponseDtoSuccess extends ResponseDto {

    private String message;

    public ResponseDtoSuccess(HttpStatus status, String message) {
        super(status);
        this.message = message;
    }
}
