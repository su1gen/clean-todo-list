package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.CategoryResponse;
import com.example.todolist.application.dto.TodoWithCategoryResponse;
import com.example.todolist.domain.model.Category;
import com.example.todolist.domain.model.Todo;
import com.example.todolist.domain.model.TodoStatus;
import com.example.todolist.domain.repository.CategoryRepository;
import com.example.todolist.domain.repository.TodoRepository;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
class GetInboxTodosUseCase implements GetInboxTodos {
    private final TodoRepository todoRepository;
    private final CategoryRepository categoryRepository;

    GetInboxTodosUseCase(TodoRepository todoRepository, CategoryRepository categoryRepository) {
        this.todoRepository = todoRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<TodoWithCategoryResponse> execute(Long userId) {
        var todos = todoRepository.findByUserIdAndDeletedAtIsNullAndStatusOrderByIdDesc(userId, TodoStatus.CREATED);

        Set<Long> categoryIds = todos.stream()
                .map(Todo::getCategoryId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        Map<Long, Category> categoryMap = categoryIds.isEmpty()
                ? Map.of()
                : categoryRepository.findByIdsAndDeletedAtIsNull(categoryIds)
                    .stream()
                    .collect(Collectors.toMap(Category::getId, Function.identity()));


        return todos
                .stream()
                .map(todo -> {
                    CategoryResponse categoryResponse = Optional.ofNullable(todo.getCategoryId())
                            .map(categoryMap::get)
                            .map(category -> new CategoryResponse(category.getId(), category.getTitle()))
                            .orElse(null);

                    return new TodoWithCategoryResponse(
                            todo.getId(),
                            todo.getTitle(),
                            todo.getDescription(),
                            categoryResponse,
                            todo.getStatus().getTitle(),
                            todo.getCreatedAt(),
                            todo.getPlannedAt()
                    );
                })
                .toList();
    }
}
