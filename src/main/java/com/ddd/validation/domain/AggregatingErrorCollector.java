package com.ddd.validation.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AggregatingErrorCollector implements ErrorCollector {

    private List<RuntimeException> errors = new ArrayList<>();

    @Override
    public void add(RuntimeException e) {
        this.errors.add(e);
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public List<String> getErrors() {
        return errors.stream().map(Throwable::getMessage).collect(Collectors.toList());
    }

}
