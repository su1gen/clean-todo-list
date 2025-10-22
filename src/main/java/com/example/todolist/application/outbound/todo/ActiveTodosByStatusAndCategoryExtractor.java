package com.example.todolist.application.outbound.todo;

import com.example.todolist.domain.model.todo.Todo;
import com.example.todolist.domain.model.todo.TodoStatus;

import java.util.List;

public interface ActiveTodosByStatusAndCategoryExtractor {
    /**
     * Найти Todo по статусу, юзеру и категории
     */
    List<Todo> getUserTodosByCategoryAndStatus(Long userId, Long categoryId, TodoStatus todoStatus);

}
