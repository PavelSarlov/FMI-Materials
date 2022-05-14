package com.fmi.materials.dto.response;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDto {
    private LocalDateTime timestamp;
    private int status;
    private String error;

    public ResponseDto(HttpStatus status, String error) {
        this.timestamp = LocalDateTime.now();
        this.status = status.value();
        this.error = error;
    }
}
