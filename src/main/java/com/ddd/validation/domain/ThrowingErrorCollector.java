package com.ddd.validation.domain;

import com.ddd.validation.application.ErrorCollector;

public class ThrowingErrorCollector implements ErrorCollector {

    @Override
    public void add(RuntimeException e) {
        throw e;
    }
}
