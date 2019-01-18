package com.ddd.validation.domain;

import com.ddd.validation.application.ErrorCollector;

public class Password {

    private String value;

    private Password(String value) {
        Password.test(value, new ThrowingErrorCollector());
        this.value = value;
    }

    public static Password of(String value) {
        return new Password(value);
    }

    public static void test(String password, ErrorCollector errorCollector) {
        new PasswordValidator().validate(password, errorCollector);
    }

    private static class PasswordValidator {

        public void validate(String value, ErrorCollector errorCollector) {
            if (value == null || value.length() < 5) {
                errorCollector.add(new PasswordTooWeakException());
            }
        }

    }

    public static class PasswordTooWeakException extends RuntimeException {
        public PasswordTooWeakException() {
            super("password too weak");
        }
    }
}
