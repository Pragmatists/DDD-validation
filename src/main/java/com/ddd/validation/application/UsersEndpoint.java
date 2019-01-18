package com.ddd.validation.application;

import com.ddd.validation.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

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
    public ResponseEntity<UserCreationResponse> createUser(@RequestBody UserCreationRequst request) {
        AggregatingErrorCollector aggregatingErrorCollector = new AggregatingErrorCollector();

        Password.test(request.password, aggregatingErrorCollector);
        Email.test(request.email, aggregatingErrorCollector);

        if (aggregatingErrorCollector.hasErrors()) {
           return ResponseEntity.status(BAD_REQUEST).body(UserCreationResponse.withErrors(aggregatingErrorCollector.getErrors()));
        } else{
            User user = userFactory.create(Password.of(request.password), Email.of(request.email));
            users.add(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(UserCreationResponse.noErrors(user.getId()));
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(usersFinder.findUserEmailById(id));
    }

    public static class UserCreationRequst {
        public String email;
        public String password;
    }

    public static class UserCreationResponse {
        public String id;
        public List<String> errors;

        public static UserCreationResponse noErrors(String id) {
            UserCreationResponse userCreationResponse = new UserCreationResponse();
            userCreationResponse.id = id;
            userCreationResponse.errors = Collections.emptyList();
            return userCreationResponse;
        }

        public static UserCreationResponse withErrors(List<String> errors) {
            UserCreationResponse userCreationResponse = new UserCreationResponse();
            userCreationResponse.id = null;
            userCreationResponse.errors = errors;
            return userCreationResponse;
        }
    }

    public class AggregatingErrorCollector implements ErrorCollector {

        private List<RuntimeException> errors = new ArrayList<>();

        @Override
        public void add(RuntimeException e) {
            this.errors.add(e);
        }

        public boolean hasErrors() {
            return !errors.isEmpty();
        }

        public List<String> getErrors() {
            return errors.stream().map(error -> error.getMessage()).collect(Collectors.toList());
        }
    }
}
