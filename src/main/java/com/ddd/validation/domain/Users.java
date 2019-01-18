package com.ddd.validation.domain;

public interface Users {

    void add(User user);

    boolean isUniqueEmail(Email email);
}
