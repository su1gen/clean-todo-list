package com.example.todolist.domain.model;

import java.util.Objects;

public class UserId {
    private final Long value;

    public UserId(Long value) {
        this.value = value;
    }

    public static UserId of(Long value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("UserId must be positive");
        }

        return new UserId(value);
    }

    public Long getValue() {
        return value;
    }

    public boolean notEmpty() {
        return Objects.nonNull(value);
    }
}
