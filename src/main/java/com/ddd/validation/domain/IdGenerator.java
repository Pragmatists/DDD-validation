package com.ddd.validation.domain;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class IdGenerator {
    
    public UserId id() {
        return UserId.of(UUID.randomUUID().toString());
    }
}
