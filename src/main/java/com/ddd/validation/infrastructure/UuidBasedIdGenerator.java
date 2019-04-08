package com.ddd.validation.infrastructure;

import com.ddd.validation.domain.IdGenerator;
import com.ddd.validation.domain.UserId;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UuidBasedIdGenerator implements IdGenerator {

    @Override
    public UserId id() {
        return UserId.of(UUID.randomUUID().toString());
    }
}
