package com.ddd.validation.domain;

public class Password {

    private String value;

    private Password(String value) {
        new PasswordValidator().validate(value);
        this.value = value;
    }

    public static Password of(String value) {
        return new Password(value);
    }

    private static class PasswordValidator {

        public void validate(String value) {
            if (value == null || value.length() < 5) {
                throw new PasswordTooWeakException();
            }
        }

    }

    public static class PasswordTooWeakException extends RuntimeException {
    }
}
