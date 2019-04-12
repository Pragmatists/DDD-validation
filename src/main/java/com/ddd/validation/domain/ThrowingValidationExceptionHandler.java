package com.ddd.validation.domain;

public class ThrowingValidationExceptionHandler implements ValidationExceptionHandler {

    @Override
    public void add(ValidationException e) {
        throw e;
    }
}
