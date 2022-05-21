package com.fmi.materials.dto.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ResponseDto {
    private LocalDateTime timestamp;
    private int status;

    public ResponseDto(HttpStatus status) {
        this.timestamp = LocalDateTime.now();
        this.status = status.value();
    }
}
