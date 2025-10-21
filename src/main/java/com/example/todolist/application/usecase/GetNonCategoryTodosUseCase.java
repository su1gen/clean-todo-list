package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.GetNonCategoryTodosDto;
import com.example.todolist.application.inbound.todo.GetNonCategoryTodos;
import com.example.todolist.application.outbound.todo.ActiveTodosByStatusWithoutCategoryExtractor;
import com.example.todolist.domain.model.Todo;
import com.example.todolist.domain.model.TodoStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class GetNonCategoryTodosUseCase implements GetNonCategoryTodos {
    private final ActiveTodosByStatusWithoutCategoryExtractor extractor;

    GetNonCategoryTodosUseCase(ActiveTodosByStatusWithoutCategoryExtractor extractor) {
        this.extractor = extractor;
    }

    @Override
    public List<Todo> execute(GetNonCategoryTodosDto getNonCategoryTodosDto) {
        TodoStatus status = TodoStatus.fromUrlParam(getNonCategoryTodosDto.status());

        return extractor.getUserTodosWithoutCategoryByStatus(
                getNonCategoryTodosDto.userId(),
                status);
    }
}
