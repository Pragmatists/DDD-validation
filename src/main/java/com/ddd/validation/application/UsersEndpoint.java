package com.ddd.validation.application;

import com.ddd.validation.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class UsersEndpoint {

    private final UsersFinder usersFinder;
    private final Users users;
    private final UserFactory userFactory;

    public UsersEndpoint(UsersFinder usersFinder, Users users, UserFactory userFactory) {
        this.usersFinder = usersFinder;
        this.users = users;
        this.userFactory = userFactory;
    }

    @PostMapping("/users")
    public ResponseEntity<ErrorResponseJson> createUser(@RequestBody UserCreationRequst request) {
        User user = userFactory.create(request.email, request.password);
        users.add(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(ErrorResponseJson.noErrors(user.getId()));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(usersFinder.findUserEmailById(id));
    }

    public static class UserCreationRequst {
        public String email;
        public String password;
    }

    public static class ErrorResponseJson {
        public String id;
        public List<String> errors;


        public static ErrorResponseJson noErrors(String id) {
            ErrorResponseJson errorResponseJson = new ErrorResponseJson();
            errorResponseJson.id = id;
            errorResponseJson.errors = Collections.emptyList();
            return errorResponseJson;
        }
    }
}
