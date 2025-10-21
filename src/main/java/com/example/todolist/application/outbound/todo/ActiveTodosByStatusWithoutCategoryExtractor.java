package com.example.todolist.application.outbound.todo;

import com.example.todolist.domain.model.Todo;
import com.example.todolist.domain.model.TodoStatus;

import java.util.List;

public interface ActiveTodosByStatusWithoutCategoryExtractor {
    /**
     * Найти Todo по статусу, юзеру
     */
    List<Todo> getUserTodosWithoutCategoryByStatus(Long userId, TodoStatus todoStatus);

}
