package com.example.todolist.infrastructure.persistence.repository;

import com.example.todolist.domain.model.Todo;
import com.example.todolist.domain.model.TodoStatus;
import com.example.todolist.domain.repository.TodoRepository;
import com.example.todolist.infrastructure.persistence.entity.TodoEntity;
import com.example.todolist.infrastructure.persistence.mapper.TodoMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Реализация доменного репозитория через JPA.
 */
@Component
class TodoRepositoryImpl implements TodoRepository {

    private final JpaTodoRepository jpaRepository;
    private final TodoMapper mapper;

    TodoRepositoryImpl(JpaTodoRepository jpaRepository, TodoMapper mapper) {
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
    public Optional<Todo> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Todo> findByIdAndNotDeleted(Long id) {
        return jpaRepository.findByIdAndDeletedAtIsNull(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Todo> findByUserIdAndNotDeleted(Long userId) {
        return jpaRepository.findByUserIdAndDeletedAtIsNull(userId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Todo> findByUserIdAndStatusAndNotDeleted(Long userId, TodoStatus status) {
        return jpaRepository.findByUserIdAndStatusAndDeletedAtIsNull(userId, status).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Todo> findByUserIdAndCategoryIdAndNotDeleted(Long userId, Long categoryId) {
        return jpaRepository.findByUserIdAndCategoryIdAndDeletedAtIsNull(userId, categoryId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Todo> findOverdueTodosByUserId(Long userId, LocalDateTime now) {
        return jpaRepository.findOverdueTodosByUserId(
                        userId,
                        now,
                        TodoStatus.FINISHED,
                        TodoStatus.NOT_RELEVANT
                ).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Todo> findTodayTodosByUserId(Long userId, LocalDateTime startOfDay, LocalDateTime endOfDay) {
        return jpaRepository.findTodayTodosByUserId(userId, startOfDay, endOfDay).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
