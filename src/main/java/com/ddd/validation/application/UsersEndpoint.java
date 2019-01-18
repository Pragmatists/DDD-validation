package com.ddd.validation.application;

import com.ddd.validation.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsersEndpoint {

    private final UsersFinder usersFinder;
    private final Users users;
    private final IdGenerator idGenerator;

    public UsersEndpoint(UsersFinder usersFinder, Users users, IdGenerator idGenerator) {
        this.usersFinder = usersFinder;
        this.users = users;
        this.idGenerator = idGenerator;
    }

    @PostMapping("/users")
    public ResponseEntity<String> createUser(@RequestBody String email) {
        User user = new User(idGenerator.id(), Email.of(email));
        users.add(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user.getId());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(usersFinder.findUserEmailById(id));
    }
}
