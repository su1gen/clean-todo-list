package com.example.todolist.application.usecase;

import com.example.todolist.application.inbound.todo.GetInboxTodos;
import com.example.todolist.application.outbound.todo.ActiveTodosByStatusExtractor;
import com.example.todolist.domain.model.Todo;
import com.example.todolist.domain.model.TodoStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class GetInboxTodosUseCase implements GetInboxTodos {
    private final ActiveTodosByStatusExtractor todosByStatusExtractor;

    GetInboxTodosUseCase(ActiveTodosByStatusExtractor todosByStatusExtractor) {
        this.todosByStatusExtractor = todosByStatusExtractor;
    }

    @Override
    public List<Todo> execute(Long userId) {
        return todosByStatusExtractor.getUserTodosByStatus(userId, TodoStatus.CREATED);
    }
}
