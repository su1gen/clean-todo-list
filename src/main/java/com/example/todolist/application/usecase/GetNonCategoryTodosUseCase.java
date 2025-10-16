package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.GetNonCategoryTodosDto;
import com.example.todolist.application.inbound.todo.GetNonCategoryTodos;
import com.example.todolist.application.outbound.todo.ActiveTodosByStatusAndCategoryExtractor;
import com.example.todolist.domain.model.Todo;
import com.example.todolist.domain.model.TodoStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class GetNonCategoryTodosUseCase implements GetNonCategoryTodos {
    private final ActiveTodosByStatusAndCategoryExtractor activeTodosByStatusAndCategoryExtractor;

    GetNonCategoryTodosUseCase(ActiveTodosByStatusAndCategoryExtractor activeTodosByStatusAndCategoryExtractor) {
        this.activeTodosByStatusAndCategoryExtractor = activeTodosByStatusAndCategoryExtractor;
    }

    @Override
    public List<Todo> execute(GetNonCategoryTodosDto getNonCategoryTodosDto) {
        TodoStatus status = TodoStatus.fromUrlParam(getNonCategoryTodosDto.status());

        return activeTodosByStatusAndCategoryExtractor.getUserTodosByCategoryAndStatus(
                getNonCategoryTodosDto.userId(),
                null,
                status);
    }
}
