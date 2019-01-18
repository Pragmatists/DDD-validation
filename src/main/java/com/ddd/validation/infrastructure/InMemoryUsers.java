package com.ddd.validation.infrastructure;

import com.ddd.validation.domain.User;
import com.ddd.validation.domain.Users;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryUsers implements Users {

    private final InMemoryUserStorage storage;

    public InMemoryUsers(InMemoryUserStorage storage) {
        this.storage = storage;
    }

    @Override
    public void add(User user) {
        storage.add(user);
    }
}
