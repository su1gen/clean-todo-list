package com.example.todolist.application.outbound.todo;

import com.example.todolist.domain.model.todo.Todo;
import com.example.todolist.domain.model.todo.TodoStatus;

import java.util.List;

public interface ActiveTodosByStatusExtractor {
    /**
     * Найти Todo по статусу и юзеру
     */
    List<Todo> getUserTodosByStatus(Long userId, TodoStatus todoStatus);

}
