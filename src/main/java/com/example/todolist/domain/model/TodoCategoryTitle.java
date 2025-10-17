package com.example.todolist.domain.model;

public class TodoCategoryTitle {
    private final String value;

    private TodoCategoryTitle(String value) {
        this.value = value;
    }

    public static TodoCategoryTitle of(String title) {
        if (title.length() > 255) {
            throw new IllegalArgumentException("Title cannot exceed 255 characters");
        }
        return new TodoCategoryTitle(title.trim());
    }

    public String getValue() {
        return value;
    }
}
