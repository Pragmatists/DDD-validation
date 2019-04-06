package com.ddd.validation.domain;

public interface ErrorCollector {
    void add(RuntimeException e);
}
