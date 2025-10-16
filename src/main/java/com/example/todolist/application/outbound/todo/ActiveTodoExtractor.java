package com.example.todolist.application.outbound.todo;

import com.example.todolist.domain.model.Todo;

import java.util.Optional;

public interface ActiveTodoExtractor {
    /**
     * Найти Todo по ID только если не удалена
     */
    Optional<Todo> findById(Long id);
}
