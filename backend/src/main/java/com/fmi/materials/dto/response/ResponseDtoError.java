package com.fmi.materials.dto.response;

import org.springframework.http.HttpStatus;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
public class ResponseDtoError<T> extends ResponseDto {

    private T error;

    public ResponseDtoError(HttpStatus status, T error) {
        super(status);
        this.error = error;
    }
}
