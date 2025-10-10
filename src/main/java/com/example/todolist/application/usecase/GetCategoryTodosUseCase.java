package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.CategoryWithTodosResponse;
import com.example.todolist.application.dto.TodoResponse;
import com.example.todolist.application.dto.TodoStatusResponse;
import com.example.todolist.domain.exception.CategoryNotFoundException;
import com.example.todolist.domain.model.TodoStatus;
import com.example.todolist.domain.repository.CategoryRepository;
import com.example.todolist.domain.repository.TodoRepository;
import org.springframework.stereotype.Component;

@Component
class GetCategoryTodosUseCase implements GetCategoryTodos {
    private final TodoRepository todoRepository;
    private final CategoryRepository categoryRepository;

    GetCategoryTodosUseCase(TodoRepository todoRepository, CategoryRepository categoryRepository) {
        this.todoRepository = todoRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryWithTodosResponse execute(Long userId, Long categoryId, String statusUrlParam) {
        var status = TodoStatus.fromUrlParam(statusUrlParam);
        var category = categoryRepository.findByIdAndNotDeleted(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        var todos = todoRepository.findByUserIdAndCategoryIdAndDeletedAtIsNullAndStatusOrderByIdDesc(userId, categoryId, status);

        return new CategoryWithTodosResponse(
                category.getTitle(),
                todos
                        .stream()
                        .map(item -> new TodoResponse(
                                item.getId(),
                                item.getTitle(),
                                item.getDescription(),
                                item.getCategoryId(),
                                new TodoStatusResponse(
                                        item.getStatus().getId(),
                                        item.getStatus().getTitle()
                                ),
                                item.getCreatedAt(),
                                item.getPlannedAt()
                        ))
                        .toList()
        );
    }
}
