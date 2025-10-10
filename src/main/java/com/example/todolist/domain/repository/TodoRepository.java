package com.example.todolist.domain.repository;

import com.example.todolist.domain.model.Todo;
import com.example.todolist.domain.model.TodoStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TodoRepository {
    /**
     * Сохранить Todo (создать или обновить)
     */
    Todo save(Todo todo);

    /**
     * Найти Todo по ID только если не удалена
     */
    Optional<Todo> findByIdAndNotDeleted(Long id);

    /**
     * Найти Today Todo
     */
    List<Todo> findByUserIdAndDeletedAtIsNullAndPlannedAtBetweenOrderByIdDesc(Long userId, LocalDateTime startOfDay, LocalDateTime endOfDay);


    /**
     * Найти Todo по статусу и юзеру
     */
    List<Todo> findByUserIdAndDeletedAtIsNullAndStatusOrderByIdDesc(Long userId, TodoStatus todoStatus);

    /**
     * Найти Todo по статусу, юзеру и категории
     */
    List<Todo> findByUserIdAndCategoryIdAndDeletedAtIsNullAndStatusOrderByIdDesc(Long userId, Long categoryId, TodoStatus todoStatus);
}
