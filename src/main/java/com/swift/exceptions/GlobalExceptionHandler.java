package com.swift.exceptions;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Hidden
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    ProblemDetail handleIllegalArgumentException(IllegalArgumentException e) {
        return ProblemDetail
                .forStatusAndDetail(
                        HttpStatus.BAD_REQUEST,
                        e.getMessage()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errors = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .toList()
                .toString();

        return ProblemDetail
                .forStatusAndDetail(
                        HttpStatus.BAD_REQUEST,
                        errors
                );
    }

    @ExceptionHandler(Exception.class)
    ProblemDetail handleException(Exception e) {
        return ProblemDetail
                .forStatusAndDetail(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        e.getMessage()
                );
    }

}
