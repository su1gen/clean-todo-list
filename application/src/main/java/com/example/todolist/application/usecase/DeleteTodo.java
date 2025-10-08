package com.example.todolist.application.usecase;

public interface DeleteTodo {
    void execute(Long todoId, Long userId);

    void restore(Long todoId, Long userId);
}
