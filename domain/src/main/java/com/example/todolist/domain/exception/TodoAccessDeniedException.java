package com.example.todolist.domain.exception;

public class TodoAccessDeniedException extends RuntimeException{
    public TodoAccessDeniedException(Long todoId, Long userId) {
        super("User " + userId + " does not have access to todo " + todoId);
    }
}
