package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.TodoWithCategoryDto;
import com.example.todolist.application.inbound.todo.GetInboxTodos;
import com.example.todolist.application.outbound.category.CategoriesExtractor;
import com.example.todolist.application.outbound.todo.ActiveTodosByStatusExtractor;
import com.example.todolist.domain.model.Category;
import com.example.todolist.domain.model.TodoStatus;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
class GetInboxTodosUseCase implements GetInboxTodos {
    private final ActiveTodosByStatusExtractor todosByStatusExtractor;
    private final CategoriesExtractor categoriesExtractor;

    GetInboxTodosUseCase(ActiveTodosByStatusExtractor todosByStatusExtractor, CategoriesExtractor categoriesExtractor) {
        this.todosByStatusExtractor = todosByStatusExtractor;
        this.categoriesExtractor = categoriesExtractor;
    }

    @Override
    public List<TodoWithCategoryDto> execute(Long userId) {
        var todos = todosByStatusExtractor.getUserTodosByStatus(userId, TodoStatus.CREATED);

        Set<Long> categoryIds = todos.stream()
                .map(item -> item.getCategoryId().getValue())
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        Map<Long, Category> categoryMap = categoryIds.isEmpty()
                ? Map.of()
                : categoriesExtractor.getCategoriesByIds(categoryIds)
                    .stream()
                    .collect(Collectors.toMap(
                            item -> item.getId().getValue(),
                            Function.identity())
                    );


        return todos
                .stream()
                .map(todo -> {
                    Category category = Optional.ofNullable(todo.getCategoryId().getValue())
                            .map(categoryMap::get)
                            .orElse(null);

                    return new TodoWithCategoryDto(
                            todo.getId().getValue(),
                            todo.getTitle().getValue(),
                            todo.getDescription(),
                            category,
                            todo.getStatus(),
                            todo.getCreatedAt(),
                            todo.getPlannedAt()
                    );
                })
                .toList();
    }
}
