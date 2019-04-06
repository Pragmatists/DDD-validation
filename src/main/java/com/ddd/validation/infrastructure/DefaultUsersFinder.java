package com.ddd.validation.infrastructure;

import com.ddd.validation.domain.UsersFinder;
import org.springframework.stereotype.Component;

@Component
public class DefaultUsersFinder implements UsersFinder {

    private final InMemoryUserStorage storage;

    public DefaultUsersFinder(InMemoryUserStorage storage) {
        this.storage = storage;
    }

    @Override
    public String findUserEmailById(String id) {
        return storage.stream()
                .filter(user -> user.getId().equals(id))
                .map(user -> user.getEmail().toString())
                .findFirst()
                .orElseThrow(() -> new RuntimeException(String.format("No user with id = '%s'", id)));
    }
}
