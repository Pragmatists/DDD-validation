package com.ddd.validation.domain;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Email {

    private String email;

    private Email(String email) {
        Email.test(email, new ThrowingValidationExceptionHandler());
        this.email = email;
    }

    public static Email of(String email) {
        return new Email(email);
    }

    public static void test(String email, ValidationExceptionHandler validationExceptionHandler) {
        new EmailValidator().validate(email, validationExceptionHandler);
    }

    @Override
    public String toString() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email1 = (Email) o;
        return email.equals(email1.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash("Email", email);
    }

    private static class EmailValidator {

        private final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        public void validate(String email, ValidationExceptionHandler validationExceptionHandler) {
            Matcher matcher = EMAIL_PATTERN.matcher(email);

            if (!matcher.matches()) {
                validationExceptionHandler.add(new BadEmailException(email));
            }
        }
    }

    public static class BadEmailException extends ValidationException {
        public BadEmailException(String address) {
            super("Bad email - " + address);
        }
    }
}
