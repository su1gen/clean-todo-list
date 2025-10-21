package com.example.todolist.domain.model.category;

import jakarta.annotation.Nonnull;

import java.util.Objects;

public class TodoCategoryTitle {

    public static final String EMPTY_TITLE_VALUE = "";
    private static final int MAX_TITLE_LENGTH = 255;

    private final String value;

    private TodoCategoryTitle(String value) {
        this.value = value;
    }

    @Nonnull
    public static TodoCategoryTitle of(String title) {
        // what if title is null
        Objects.requireNonNull(title);
        if (title.length() > MAX_TITLE_LENGTH) {
            throw new IllegalArgumentException("Title cannot exceed 255 characters");
        }
        return new TodoCategoryTitle(title.trim());
    }

    public String getValue() {
        return value;
    }
}
