package com.example.todolist.infrastructure.persistence.repository;

import com.example.todolist.application.outbound.todo.*;
import com.example.todolist.domain.model.todo.Todo;
import com.example.todolist.domain.model.todo.TodoStatus;
import com.example.todolist.infrastructure.persistence.entity.TodoEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


/**
 * Реализация доменного репозитория через JPA.
 */
@Component
public class TodoRepositoryImpl implements
        TodoPersister,
        TodoUpdater,
        ActiveTodoExtractor,
        ActiveTodosByStatusExtractor,
        ActiveTodosByStatusAndCategoryExtractor,
        ActiveTodosByStatusWithoutCategoryExtractor,
        TodayActiveTodosExtractor,
        TodoNextIdExtractor {

    private final JpaTodoRepository jpaRepository;

    public TodoRepositoryImpl(JpaTodoRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Todo> findById(Long id) {
        return jpaRepository.findByIdAndDeletedAtIsNull(id)
                .map(TodoEntity::toDomain);
    }

    @Override
    public List<Todo> getUserTodosByCategoryAndStatus(Long userId, Long categoryId, TodoStatus todoStatus) {
        List<TodoEntity> todos = jpaRepository.findByUserIdAndCategoryIdAndDeletedAtIsNullAndStatusOrderByIdDesc(userId, categoryId, todoStatus);

        System.out.println("todos111: " + todos);

        return todos
                .stream()
                .map(TodoEntity::toDomain)
                .toList();
    }

    @Override
    public List<Todo> getUserTodosWithoutCategoryByStatus(Long userId, TodoStatus todoStatus) {
        List<TodoEntity> todos = jpaRepository.findByUserIdAndCategoryIdIsNullAndDeletedAtIsNullAndStatusOrderByIdDesc(userId, todoStatus);

        return todos
                .stream()
                .map(TodoEntity::toDomain)
                .toList();
    }

    @Override
    public List<Todo> getUserTodosByStatus(Long userId, TodoStatus todoStatus) {
        List<TodoEntity> todayTodos = jpaRepository.findByUserIdAndDeletedAtIsNullAndStatusOrderByIdDesc(userId, todoStatus);

        return todayTodos
                .stream()
                .map(TodoEntity::toDomain)
                .toList();
    }

    @Override
    public List<Todo> getUserTodayTodos(Long userId) {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusSeconds(1);

        List<TodoEntity> todayTodos = jpaRepository.findByDeletedAtIsNullAndPlannedAtBetweenOrderByIdDesc(userId, startOfDay, endOfDay);

        return todayTodos
                .stream()
                .map(TodoEntity::toDomain)
                .toList();
    }

    @Override
    public Todo persist(Todo todo) {
        TodoEntity entity = TodoEntity.fromDomain(todo);
        TodoEntity savedEntity = jpaRepository.save(entity);
        return TodoEntity.toDomain(savedEntity);
    }

    @Override
    public Todo update(Todo todo) {
        TodoEntity entity = TodoEntity.fromDomain(todo);
        TodoEntity savedEntity = jpaRepository.save(entity);
        return TodoEntity.toDomain(savedEntity);
    }

    @Override
    public Long getNextTodoId() {
        return jpaRepository.getNextTodoId();
    }
}
