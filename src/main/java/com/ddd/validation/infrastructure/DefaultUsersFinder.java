package com.ddd.validation.infrastructure;

import com.ddd.validation.domain.Email;
import com.ddd.validation.domain.User;
import com.ddd.validation.domain.UserId;
import com.ddd.validation.domain.UsersFinder;
import org.springframework.stereotype.Component;

@Component
public class DefaultUsersFinder implements UsersFinder {

    private final InMemoryUserStorage storage;

    public DefaultUsersFinder(InMemoryUserStorage storage) {
        this.storage = storage;
    }

    @Override
    public Email findUserEmailById(UserId id) {
        return storage.stream()
                .filter(user -> user.getId().equals(id))
                .map(User::getEmail)
                .findFirst()
                .orElseThrow(() -> new RuntimeException(String.format("No user with id = '%s'", id)));
    }
}
