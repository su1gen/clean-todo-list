package com.example.todolist.application.outbound.todo;

public interface TodoNextIdExtractor {
    /**
     * получить следующий ID
     */
    Long getNextTodoId();
}
