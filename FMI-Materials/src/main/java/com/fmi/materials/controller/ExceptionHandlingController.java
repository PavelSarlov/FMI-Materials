package com.fmi.materials.controller;

import com.fmi.materials.dto.response.ResponseDto;
import com.fmi.materials.exception.EntityNotFoundException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ResponseDto> handleCustomException(CustomException ex) {
        ResponseDto response = new ResponseDto(HttpStatus.NOT_FOUND, ex.getMessage());
        System.out.println(response);
        return buildResponseEntity(response);
    }

    private ResponseEntity<ResponseDto> buildResponseEntity(ResponseDto responseDto) {
        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.valueOf(responseDto.getStatus()));
    }
}
