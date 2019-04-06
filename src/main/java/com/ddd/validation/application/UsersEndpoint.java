package com.ddd.validation.application;

import com.ddd.validation.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
public class UsersEndpoint {

    private final UsersFinder usersFinder;
    private final Users users;
    private final User.UserFactory userFactory;

    public UsersEndpoint(UsersFinder usersFinder, Users users, User.UserFactory userFactory) {
        this.usersFinder = usersFinder;
        this.users = users;
        this.userFactory = userFactory;
    }

    @PostMapping("/users")
    public ResponseEntity<UserCreationResponse> handleCreateUserRequest(@RequestBody UserCreationRequest request) {
        List<String> errors = validateRequest(request);
        if (errors.isEmpty()) {
            User newUser = saveNewUser(request.email, request.password);
            return ResponseEntity.status(HttpStatus.CREATED).body(UserCreationResponse.success(newUser.getId()));
        } else {
            return ResponseEntity.status(BAD_REQUEST).body(UserCreationResponse.failure(errors));
        }
    }

    private List<String> validateRequest(UserCreationRequest request) {
        AggregatingErrorCollector errorCollector = new AggregatingErrorCollector();

        Email.test(request.email, errorCollector);
        Password.test(request.password, errorCollector);
        if (errorCollector.hasErrors()) {
            return errorCollector.getErrors();
        }

        userFactory.test(Email.of(request.email), errorCollector);
        if (errorCollector.hasErrors()) {
            return errorCollector.getErrors();
        }

        return Collections.emptyList();
    }

    private User saveNewUser(String email, String password) {
        User user = userFactory.create(Password.of(password), Email.of(email));
        users.add(user);
        return user;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Object> getUserEmailById(@PathVariable String id) {
        return ResponseEntity.ok(usersFinder.findUserEmailById(id));
    }

    public static class UserCreationRequest {
        public String email;
        public String password;
    }

    public static class UserCreationResponse {
        public String id;
        public List<String> errors;

        public static UserCreationResponse success(String id) {
            UserCreationResponse userCreationResponse = new UserCreationResponse();
            userCreationResponse.id = id;
            userCreationResponse.errors = Collections.emptyList();
            return userCreationResponse;
        }

        public static UserCreationResponse failure(List<String> errors) {
            UserCreationResponse userCreationResponse = new UserCreationResponse();
            userCreationResponse.id = null;
            userCreationResponse.errors = errors;
            return userCreationResponse;
        }
    }

}
