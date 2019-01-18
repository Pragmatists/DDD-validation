package com.ddd.validation.application;

import com.ddd.validation.domain.BadEmailException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(BadEmailException.class)
    public ResponseEntity<Object> badEmail() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
