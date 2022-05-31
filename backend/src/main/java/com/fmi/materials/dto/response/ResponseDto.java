package com.fmi.materials.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto {
    private LocalDateTime timestamp;
    private int status;

    public ResponseDto(HttpStatus status) {
        this.timestamp = LocalDateTime.now();
        this.status = status.value();
    }
}
