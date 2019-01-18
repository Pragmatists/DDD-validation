package com.ddd.validation.domain;

public class User {

    private final String id;
    private final Email email;

    public User(String id, Email email) {
        this.id = id;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public Email getEmail() {
        return email;
    }
}
