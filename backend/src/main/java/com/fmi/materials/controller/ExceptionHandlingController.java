package com.fmi.materials.controller;

import com.fmi.materials.dto.response.ResponseDto;
import com.fmi.materials.dto.response.ResponseDtoError;
import com.fmi.materials.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<Object> handleCustomException(CustomException ex) {
        log.info(ex.getMessage());
        ResponseDto response = new ResponseDtoError(ex.getStatus(), ex.getMessage());
        return buildResponseEntity(response);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders httpHeaders, HttpStatus httpStatus, WebRequest request) {
        log.trace(getStackTrace(ex));
        ResponseDto response = new ResponseDtoError(HttpStatus.BAD_REQUEST, ex.getFieldErrors());
        return buildResponseEntity(response);
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
        log.trace(getStackTrace(ex));
        ResponseDto response = new ResponseDtoError(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
        return buildResponseEntity(response);
    }

    private ResponseEntity<Object> buildResponseEntity(ResponseDto responseDto) {
        return new ResponseEntity<Object>(responseDto, HttpStatus.valueOf(responseDto.getStatus()));
    }

    private String getStackTrace(final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }
}
