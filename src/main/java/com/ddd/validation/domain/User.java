package com.ddd.validation.domain;

import com.ddd.validation.application.ErrorCollector;
import org.springframework.stereotype.Component;

public class User {

    private final String id;
    private final Email email;
    private final Password password;

    private User(String id, Email email, Password password) {
        this.id = id;
        this.email = email;
        this.password = password;
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

        public void validate(Email email, ErrorCollector errorCollector) {
            if (users.isUniqueEmail(email)){
                errorCollector.add(new NotUniqueEmailAddress(email));
            }
        }
    }

    @Component
    public static class UserFactory {

        private Users users;
        private IdGenerator idGenerator;

        public UserFactory(Users users, IdGenerator idGenerator) {
            this.users = users;
            this.idGenerator = idGenerator;
        }

        public User create(Password password, Email email) {
            test(email, new ThrowingErrorCollector());
            return new User(idGenerator.id(), email, password);
        }

        public void test(Email email, ErrorCollector errorCollector) {
            new EmailUniquenessValidator(users).validate(email, errorCollector);
        }
    }

    public static class NotUniqueEmailAddress extends RuntimeException {
        public NotUniqueEmailAddress(Email email) {
            super(String.format("Not unique email address - '%s'", email));
        }
    }
}
