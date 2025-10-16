package com.example.todolist.infrastructure.persistence.repository;

import com.example.todolist.application.outbound.todo.*;
import com.example.todolist.domain.model.Todo;
import com.example.todolist.domain.model.TodoStatus;
import com.example.todolist.infrastructure.persistence.entity.TodoEntity;
import com.example.todolist.infrastructure.persistence.mapper.TodoMapper;
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
        TodayActiveTodosExtractor,
        TodoNextIdExtractor {

    private final JpaTodoRepository jpaRepository;
    private final TodoMapper mapper;

    public TodoRepositoryImpl(JpaTodoRepository jpaRepository, TodoMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Todo> findById(Long id) {
        return jpaRepository.findByIdAndDeletedAtIsNull(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Todo> getUserTodosByCategoryAndStatus(Long userId, Long categoryId, TodoStatus todoStatus) {
        List<TodoEntity> todayTodos = jpaRepository.findByUserIdAndCategoryIdAndDeletedAtIsNullAndStatusOrderByIdDesc(userId, categoryId, todoStatus);

        return todayTodos
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Todo> getUserTodosByStatus(Long userId, TodoStatus todoStatus) {
        List<TodoEntity> todayTodos = jpaRepository.findByUserIdAndDeletedAtIsNullAndStatusOrderByIdDesc(userId, todoStatus);

        return todayTodos
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Todo> getUserTodayTodos(Long userId) {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusSeconds(1);

        List<TodoEntity> todayTodos = jpaRepository.findByDeletedAtIsNullAndPlannedAtBetweenOrderByIdDesc(userId, startOfDay, endOfDay);

        return todayTodos
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Todo persist(Todo todo) {
        TodoEntity entity = mapper.toEntity(todo);
        TodoEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Todo update(Todo todo) {
        TodoEntity entity = mapper.toEntity(todo);
        TodoEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Long getNextTodoId() {
        return jpaRepository.getNextTodoId();
    }
}
