package com.example.todolist.infrastructure.persistence.repository;

import com.example.todolist.domain.model.Todo;
import com.example.todolist.domain.model.TodoStatus;
import com.example.todolist.domain.repository.TodoRepository;
import com.example.todolist.infrastructure.persistence.entity.TodoEntity;
import com.example.todolist.infrastructure.persistence.mapper.TodoMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Реализация доменного репозитория через JPA.
 */
@Component
public class TodoRepositoryImpl implements TodoRepository {

    private final JpaTodoRepository jpaRepository;
    private final TodoMapper mapper;

    public TodoRepositoryImpl(JpaTodoRepository jpaRepository, TodoMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Todo save(Todo todo) {
        TodoEntity entity = mapper.toEntity(todo);
        TodoEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Todo> findByIdAndNotDeleted(Long id) {
        return jpaRepository.findByIdAndDeletedAtIsNull(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Todo> findByUserIdAndDeletedAtIsNullAndPlannedAtBetweenOrderByIdDesc(Long userId, LocalDateTime startOfDay, LocalDateTime endOfDay) {
        List<TodoEntity> todayTodos = jpaRepository.findByDeletedAtIsNullAndPlannedAtBetweenOrderByIdDesc(userId, startOfDay, endOfDay);

        return todayTodos
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Todo> findByUserIdAndDeletedAtIsNullAndStatusOrderByIdDesc(Long userId, TodoStatus todoStatus) {
        List<TodoEntity> todayTodos = jpaRepository.findByUserIdAndDeletedAtIsNullAndStatusOrderByIdDesc(userId, todoStatus);

        return todayTodos
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Todo> findByUserIdAndCategoryIdAndDeletedAtIsNullAndStatusOrderByIdDesc(Long userId, Long categoryId, TodoStatus todoStatus) {
        List<TodoEntity> todayTodos = jpaRepository.findByUserIdAndCategoryIdAndDeletedAtIsNullAndStatusOrderByIdDesc(userId, categoryId, todoStatus);

        return todayTodos
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Long getNextTodoId() {
        return jpaRepository.getNextTodoId();
    }
}
