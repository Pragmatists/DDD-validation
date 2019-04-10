package com.ddd.validation.domain;

public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}
