package com.example.todolist.application.outbound.todo;

import com.example.todolist.domain.model.Todo;
import com.example.todolist.domain.model.TodoStatus;

import java.util.List;

public interface ActiveTodosByStatusExtractor {
    /**
     * Найти Todo по статусу и юзеру
     */
    List<Todo> getUserTodosByStatus(Long userId, TodoStatus todoStatus);

}
