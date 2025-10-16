package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.CategoryWithTodosDto;
import com.example.todolist.application.dto.GetCategoryTodosDto;
import com.example.todolist.application.inbound.todo.GetCategoryTodos;
import com.example.todolist.application.outbound.category.ActiveCategoryExtractor;
import com.example.todolist.application.outbound.todo.ActiveTodosByStatusAndCategoryExtractor;
import com.example.todolist.domain.exception.CategoryNotFoundException;
import com.example.todolist.domain.model.Category;
import com.example.todolist.domain.model.Todo;
import com.example.todolist.domain.model.TodoStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class GetCategoryTodosUseCase implements GetCategoryTodos {
    private final ActiveTodosByStatusAndCategoryExtractor todosByStatusAndCategoryExtractor;
    private final ActiveCategoryExtractor activeCategoryExtractor;

    GetCategoryTodosUseCase(
            ActiveTodosByStatusAndCategoryExtractor todosByStatusAndCategoryExtractor,
            ActiveCategoryExtractor activeCategoryExtractor) {
        this.todosByStatusAndCategoryExtractor = todosByStatusAndCategoryExtractor;
        this.activeCategoryExtractor = activeCategoryExtractor;
    }

    @Override
    public CategoryWithTodosDto execute(GetCategoryTodosDto getCategoryTodosDto) {
        TodoStatus status = TodoStatus.fromUrlParam(getCategoryTodosDto.status());
        Category category = activeCategoryExtractor.getActiveCategoryById(getCategoryTodosDto.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException(getCategoryTodosDto.categoryId()));

        List<Todo> todos = todosByStatusAndCategoryExtractor.getUserTodosByCategoryAndStatus(
                getCategoryTodosDto.userId(),
                getCategoryTodosDto.categoryId(),
                status
        );

        return new CategoryWithTodosDto(
                category,
                todos
        );
    }
}
