package com.ddd.validation.domain;

public class Password {

    private String value;

    private Password(String value) {
        Password.test(value, new ThrowingValidationExceptionHandler());
        this.value = value;
    }

    public static Password of(String value) {
        return new Password(value);
    }

    public static void test(String password, ValidationExceptionHandler validationExceptionHandler) {
        new PasswordValidator().validate(password, validationExceptionHandler);
    }

    private static class PasswordValidator {

        public void validate(String value, ValidationExceptionHandler validationExceptionHandler) {
            if (value == null || value.length() < 5) {
                validationExceptionHandler.add(new PasswordTooWeakException());
            }
        }

    }

    public static class PasswordTooWeakException extends ValidationException {
        public PasswordTooWeakException() {
            super("Password too weak");
        }
    }
}
