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

    public User create(Password pass, Email email) {
        return new User(idGenerator.id(), email, pass, new User.EmailUniquenessValidator(users));
    }

}
