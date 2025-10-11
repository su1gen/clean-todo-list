package com.example.todolist.application.outport;

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
     * Найти Todo по ID (включая удалённые)
     */
    Optional<Todo> findById(Long id);

    /**
     * Найти Todo по ID только если не удалена
     */
    Optional<Todo> findByIdAndNotDeleted(Long id);

    /**
     * Получить все Todo пользователя (без удалённых)
     */
    List<Todo> findByUserIdAndNotDeleted(Long userId);

    /**
     * Получить Todo пользователя с фильтрацией по статусу
     */
    List<Todo> findByUserIdAndStatusAndNotDeleted(Long userId, TodoStatus status);

    /**
     * Получить Todo пользователя по категории
     */
    List<Todo> findByUserIdAndCategoryIdAndNotDeleted(Long userId, Long categoryId);

    /**
     * Получить просроченные Todo пользователя
     */
    List<Todo> findOverdueTodosByUserId(Long userId, LocalDateTime now);

    /**
     * Получить запланированные на сегодня Todo
     */
    List<Todo> findTodayTodosByUserId(Long userId, LocalDateTime startOfDay, LocalDateTime endOfDay);

    /**
     * Проверить существование Todo по ID
     */
    boolean existsById(Long id);

    /**
     * Физическое удаление Todo
     */
    void deleteById(Long id);
}
