package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.TodoResponse;
import com.example.todolist.domain.model.Todo;
import com.example.todolist.domain.model.TodoStatus;
import com.example.todolist.domain.repository.TodoRepository;
import jakarta.inject.Named;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Use Case: Получение списка Todo пользователя с различными фильтрами.
 */
@Named
 class GetUserTodosUseCase implements GetUserTodos {

    private final TodoRepository todoRepository;

     GetUserTodosUseCase(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    /**
     * Получить все Todo пользователя (без удалённых)
     */
    @Override
    public List<TodoResponse> execute(Long userId) {
        List<Todo> todos = todoRepository.findByUserIdAndNotDeleted(userId);
        return todos.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Получить Todo пользователя с фильтрацией по статусу
     */
    @Override
    public List<TodoResponse> executeByStatus(Long userId, String statusUrlParam) {
        TodoStatus status = TodoStatus.fromUrlParam(statusUrlParam);
        List<Todo> todos = todoRepository.findByUserIdAndStatusAndNotDeleted(userId, status);
        return todos.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Получить Todo пользователя по категории
     */
    @Override
    public List<TodoResponse> executeByCategory(Long userId, Long categoryId) {
        List<Todo> todos = todoRepository.findByUserIdAndCategoryIdAndNotDeleted(userId, categoryId);
        return todos.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Получить просроченные Todo
     */
    @Override
    public List<TodoResponse> executeOverdue(Long userId) {
        List<Todo> todos = todoRepository.findOverdueTodosByUserId(userId, LocalDateTime.now());
        return todos.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Получить запланированные на сегодня Todo
     */
    @Override
    public List<TodoResponse> executeToday(Long userId) {
        LocalDateTime startOfDay = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.now().with(LocalTime.MAX);

        List<Todo> todos = todoRepository.findTodayTodosByUserId(userId, startOfDay, endOfDay);
        return todos.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private TodoResponse mapToResponse(Todo todo) {
        return new TodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.getCategoryId(),
                todo.getUserId(),
                todo.getStatus().getUrlParam(),
                todo.getStatus().getTitle(),
                todo.getCreatedAt(),
                todo.getPlannedAt(),
                todo.isDeleted(),
                todo.isOverdue()
        );
    }
}
