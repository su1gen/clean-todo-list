package com.example.todolist.application.usecase;

public interface DeleteCategory {
    void execute(Long categoryId, Long userId);

    void restore(Long categoryId, Long userId);
}
