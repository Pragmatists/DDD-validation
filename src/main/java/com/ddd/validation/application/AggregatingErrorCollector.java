package com.ddd.validation.application;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
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

    public void hasErrors(Consumer<List<String>> errorsConsumer) {
        errorsConsumer.accept(getErrors());
    }

    public AggregatingErrorCollector noErrors(Consumer<AggregatingErrorCollector> errorsConsumer) {
        if (!hasErrors()) {
            errorsConsumer.accept(this);
        }

        return this;
    }

    public <T> T finish(Function<List<String>, T> finisher){
        return finisher.apply(getErrors());
    }

    public static AggregatingErrorCollector aggregateErrors(Consumer<AggregatingErrorCollector> abc) {
        AggregatingErrorCollector errorCollector = new AggregatingErrorCollector();
        abc.accept(errorCollector);
        return errorCollector;
    }
}
