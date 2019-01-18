package com.ddd.validation.domain;

public class User {

    private final String id;
    private final Email email;
    private final Password password;

    public User(String id, Email email, Password password, EmailUniquenessValidator emailUniquenessValidator) {
        this.id = id;
        this.email = email;
        this.password = password;
        emailUniquenessValidator.validate(email);
    }

    public String getId() {
        return id;
    }

    public Email getEmail() {
        return email;
    }

    public static class EmailUniquenessValidator {

        private final Users users;

        public EmailUniquenessValidator(Users users) {
            this.users = users;
        }

        public void validate(Email email) {
            if (users.isUniqueEmail(email)){
                throw new NotUniqueEmailAddress(email);
            };
        }
    }

    public static class NotUniqueEmailAddress extends RuntimeException {
        public NotUniqueEmailAddress(Email email) {
            super(String.format("Not unique email address - '%s'", email));
        }
    }
}
