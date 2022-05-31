package com.fmi.materials.controller;

import com.fmi.materials.dto.response.ResponseDto;
import com.fmi.materials.dto.response.ResponseDtoError;
import com.fmi.materials.exception.CustomException;
import com.fmi.materials.util.CustomUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<Object> handleCustomException(CustomException ex) {
        log.info(ex.getMessage());
        return buildResponseEntity(ex.getMessage(), ex.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, List<String>> errorList = ex.getBindingResult()
                .getFieldErrors().stream()
                .collect(Collectors.groupingBy(fe -> fe.getField()))
                .entrySet().stream()
                .map(f -> new AbstractMap.SimpleEntry<String, List<String>>(f.getKey(), f.getValue().stream().map(fe -> fe.getDefaultMessage()).collect(Collectors.toList())))
                .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
        log.info(ex.getMessage());
        return buildResponseEntity(errorList, status);
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
        log.trace(CustomUtils.getStackTrace(ex));
        return buildResponseEntity("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> buildResponseEntity(Object error, HttpStatus status) {
        ResponseDto responseDto = new ResponseDtoError(status, error);
        return new ResponseEntity<Object>(responseDto, status);
    }
}
