package com.example.todolist.domain.model.category;

import java.util.Objects;

public class CategoryId {

    private static final CategoryId EMPTY = new CategoryId(null);

    private final Long value;

    private CategoryId(Long value) {
        this.value = value;
    }

    public static CategoryId of(Long value) {
        // legal case
        if (Objects.isNull(value)) {
            return empty();
        }

        if (value <= 0) {
            throw new IllegalArgumentException("CategoryId must be positive");
        }

        return new CategoryId(value);
    }

    private static CategoryId empty() {
        return EMPTY;
    }

    public Long getValue() {
        return value;
    }

    public boolean notEmpty() {
        return this != EMPTY;
    }
}
