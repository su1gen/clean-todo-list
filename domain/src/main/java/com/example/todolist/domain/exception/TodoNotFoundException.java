package com.example.todolist.domain.exception;

public class TodoNotFoundException extends RuntimeException{
    public TodoNotFoundException(Long todoId) {
        super("Todo not found with id: " + todoId);
    }
}
