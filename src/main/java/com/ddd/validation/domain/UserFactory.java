package com.ddd.validation.domain;

import org.springframework.stereotype.Component;

@Component
public class UserFactory {
    private Users users;
    private IdGenerator idGenerator;

    public UserFactory(Users users, IdGenerator idGenerator) {
        this.users = users;
        this.idGenerator = idGenerator;
    }

    public User create(String email, String password) {
        return new User(idGenerator.id(), Email.of(email), Password.of(password), new User.EmailUniquenessValidator(users));
    }

}
