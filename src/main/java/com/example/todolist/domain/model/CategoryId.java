package com.example.todolist.domain.model;

import java.util.Objects;

public class CategoryId {
    private final Long value;

    public CategoryId(Long value) {
        this.value = value;
    }

    public static CategoryId of(Long value) {
        if (value != null && value <= 0) {
            throw new IllegalArgumentException("CategoryId must be positive");
        }

        return new CategoryId(value);
    }

    public Long getValue() {
        return value;
    }

    public boolean notEmpty() {
        return Objects.nonNull(value);
    }
}
