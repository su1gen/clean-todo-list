package com.example.todolist.presentation.mapper;

import com.example.todolist.application.dto.TodoResponse;
import com.example.todolist.application.dto.TodoStatusResponse;
import com.example.todolist.domain.model.Todo;
import org.springframework.stereotype.Component;

@Component
public class TodoResponseMapper {

    public TodoResponse toResponse(Todo todo) {
        return new TodoResponse(
                todo.getId().getValue(),
                todo.getTitle().getValue(),
                todo.getDescription(),
                todo.getCategoryId().getValue(),
                new TodoStatusResponse(
                        todo.getStatus().getId(),
                        todo.getStatus().getTitle()
                ),
                todo.getCreatedAt(),
                todo.getPlannedAt()
        );
    }
}
