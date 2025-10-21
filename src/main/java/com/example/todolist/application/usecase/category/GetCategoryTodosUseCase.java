package com.example.todolist.application.usecase.category;

import com.example.todolist.application.dto.GetCategoryTodosDto;
import com.example.todolist.application.inbound.category.GetCategoryTodos;
import com.example.todolist.application.outbound.category.CategoryExister;
import com.example.todolist.application.outbound.todo.ActiveTodosByStatusAndCategoryExtractor;
import com.example.todolist.domain.model.Todo;
import com.example.todolist.domain.model.TodoStatus;
import com.example.todolist.domain.model.category.CategoryId;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class GetCategoryTodosUseCase implements GetCategoryTodos {
    private final ActiveTodosByStatusAndCategoryExtractor todosByStatusAndCategoryExtractor;
    private final CategoryExister categoryExister;

    GetCategoryTodosUseCase(
            ActiveTodosByStatusAndCategoryExtractor todosByStatusAndCategoryExtractor,
            CategoryExister categoryExister) {
        this.todosByStatusAndCategoryExtractor = todosByStatusAndCategoryExtractor;
        this.categoryExister = categoryExister;
    }

    @Override
    public List<Todo> execute(GetCategoryTodosDto getCategoryTodosDto) {
        TodoStatus status = TodoStatus.fromUrlParam(getCategoryTodosDto.status());

        categoryExister.exists(CategoryId.of(getCategoryTodosDto.categoryId()));

        return todosByStatusAndCategoryExtractor.getUserTodosByCategoryAndStatus(
                getCategoryTodosDto.userId(),
                getCategoryTodosDto.categoryId(),
                status
        );
    }
}
