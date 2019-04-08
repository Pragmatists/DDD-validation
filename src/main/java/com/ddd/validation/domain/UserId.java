package com.ddd.validation.domain;

import java.util.Objects;

public class UserId {

    private String id;

    public UserId(String id) {

        this.id = id;
    }

    public static final UserId of(String id) {
        return new UserId(id);
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserId userId = (UserId) o;
        return id.equals(userId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
