package com.ddd.validation.domain;

public class ThrowingErrorCollector implements ErrorCollector {

    @Override
    public void add(ValidationException e) {
        throw e;
    }
}
