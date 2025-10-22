package com.example.todolist.application.outbound.todo;

import com.example.todolist.domain.model.todo.Todo;
import com.example.todolist.domain.model.todo.TodoStatus;

import java.util.List;

public interface ActiveTodosByStatusWithoutCategoryExtractor {
    /**
     * Найти Todo по статусу, юзеру
     */
    List<Todo> getUserTodosWithoutCategoryByStatus(Long userId, TodoStatus todoStatus);

}
