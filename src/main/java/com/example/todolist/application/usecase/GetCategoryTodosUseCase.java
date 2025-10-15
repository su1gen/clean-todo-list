package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.CategoryWithTodosResponse;
import com.example.todolist.application.dto.TodoResponse;
import com.example.todolist.application.inbound.todo.GetCategoryTodos;
import com.example.todolist.application.outbound.category.ActiveCategoryExtractor;
import com.example.todolist.domain.exception.CategoryNotFoundException;
import com.example.todolist.domain.model.TodoStatus;
import com.example.todolist.application.outbound.TodoRepository;
import com.example.todolist.presentation.mapper.TodoResponseMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class GetCategoryTodosUseCase implements GetCategoryTodos {
    private final TodoRepository todoRepository;
    private final ActiveCategoryExtractor activeCategoryExtractor;
    private final TodoResponseMapper todoResponseMapper;

    GetCategoryTodosUseCase(TodoRepository todoRepository, ActiveCategoryExtractor activeCategoryExtractor, TodoResponseMapper todoResponseMapper) {
        this.todoRepository = todoRepository;
        this.activeCategoryExtractor = activeCategoryExtractor;
        this.todoResponseMapper = todoResponseMapper;
    }

    @Override
    public CategoryWithTodosResponse execute(Long userId, Long categoryId, String statusUrlParam) {
        var status = TodoStatus.fromUrlParam(statusUrlParam);
        var category = activeCategoryExtractor.getActiveCategoryById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        var todos = todoRepository.findByUserIdAndCategoryIdAndDeletedAtIsNullAndStatusOrderByIdDesc(userId, categoryId, status);

        List<TodoResponse> todoResponseList = todos.stream()
                .map(todoResponseMapper::toResponse)
                .toList();

        return new CategoryWithTodosResponse(
                category.getTitle().getValue(),
                todoResponseList
        );
    }
}
