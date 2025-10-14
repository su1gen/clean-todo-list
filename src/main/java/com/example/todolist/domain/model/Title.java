package com.example.todolist.domain.model;

public class Title {
    private final String value;

    public Title(String value) {
        this.value = value;
    }

    public static Title of(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        if (title.length() > 255) {
            throw new IllegalArgumentException("Title cannot exceed 255 characters");
        }
        return new Title(title.trim());
    }

    public String getValue() {
        return value;
    }
}
