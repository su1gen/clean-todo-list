package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.CategoryResponse;
import com.example.todolist.application.dto.TodoWithCategoryResponse;
import com.example.todolist.application.inbound.todo.GetTodayTodos;
import com.example.todolist.application.outbound.category.CategoriesExtractor;
import com.example.todolist.domain.model.Category;
import com.example.todolist.application.outbound.TodoRepository;
import com.example.todolist.presentation.mapper.CategoryResponseMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
class GetTodayTodosUseCase implements GetTodayTodos {
    private final TodoRepository todoRepository;
    private final CategoriesExtractor categoriesExtractor;
    private final CategoryResponseMapper categoryResponseMapper;

    GetTodayTodosUseCase(TodoRepository todoRepository, CategoriesExtractor categoriesExtractor, CategoryResponseMapper categoryResponseMapper) {
        this.todoRepository = todoRepository;
        this.categoriesExtractor = categoriesExtractor;
        this.categoryResponseMapper = categoryResponseMapper;
    }

    @Override
    public List<TodoWithCategoryResponse> execute(Long userId) {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusSeconds(1);

        var todos = todoRepository.findByUserIdAndDeletedAtIsNullAndPlannedAtBetweenOrderByIdDesc(userId, startOfDay, endOfDay);
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
                    CategoryResponse categoryResponse = Optional.ofNullable(todo.getCategoryId().getValue())
                            .map(categoryMap::get)
                            .map(categoryResponseMapper::toResponse)
                            .orElse(null);

                    return new TodoWithCategoryResponse(
                            todo.getId().getValue(),
                            todo.getTitle().getValue(),
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
