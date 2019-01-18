package com.ddd.validation.domain;

public class BadEmailException extends RuntimeException {
    public BadEmailException() {
        super("bad email");
    }
}
