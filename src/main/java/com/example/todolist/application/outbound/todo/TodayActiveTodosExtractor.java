package com.example.todolist.application.outbound.todo;

import com.example.todolist.domain.model.Todo;

import java.util.List;

public interface TodayActiveTodosExtractor {
    /**
     * Найти Today Todo
     */
    List<Todo> getUserTodayTodos(Long userId);

}
