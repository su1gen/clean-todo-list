package com.example.todolist.domain.model;

import java.util.Objects;

public class TodoId {
    private final Long value;

    private TodoId(Long value) {
        this.value = value;
    }

    public static TodoId of(Long value) {
        if (value != null && value <= 0) {
            throw new IllegalArgumentException("TodoId must be positive");
        }

        return new TodoId(value);
    }

    public Long getValue() {
        return value;
    }

    public boolean notEmpty() {
        return Objects.nonNull(value);
    }
}
