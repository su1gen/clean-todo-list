package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.TodoWithCategoryDto;
import com.example.todolist.application.inbound.todo.GetInboxTodos;
import com.example.todolist.application.outbound.category.CategoriesExtractor;
import com.example.todolist.application.outbound.todo.ActiveTodosByStatusExtractor;
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
    public List<TodoWithCategoryDto> execute(Long userId) {
        var todos = todosByStatusExtractor.getUserTodosByStatus(userId, TodoStatus.CREATED);

        return todos
                .stream()
                .map(todo -> new TodoWithCategoryDto(
                        todo.getId().getValue(),
                        todo.getTitle().getValue(),
                        todo.getDescription(),
                        todo.getCategoryTitle().getValue(),
                        todo.getStatus(),
                        todo.getCreatedAt(),
                        todo.getPlannedAt()
                ))
                .toList();
    }
}
