package com.fmi.materials.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.http.HttpStatus;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto {
    private LocalDateTime timestamp;
    private int status;

    public ResponseDto(HttpStatus status) {
        this.timestamp = LocalDateTime.now();
        this.status = status.value();
    }
}
