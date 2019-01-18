package com.ddd.validation.application;

public interface ErrorCollector {
    void add(RuntimeException e);
}
