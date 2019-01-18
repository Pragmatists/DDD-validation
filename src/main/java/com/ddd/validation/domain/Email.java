package com.ddd.validation.domain;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Email {

    private final EmailValidator validator = new EmailValidator();
    private String email;

    public Email(String email) {
        this.email = email;
        validator.validate(email);
    }

    public static Email of(String email) {
        return new Email(email);
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

    private class EmailValidator {

        private final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        public void validate(String email) {
            Matcher matcher = EMAIL_PATTERN.matcher(email);
            if (!matcher.matches()) {
                throw new BadEmailException();
            }
        }
    }


}
