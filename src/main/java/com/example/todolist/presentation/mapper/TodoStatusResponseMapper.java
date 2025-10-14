package com.example.todolist.presentation.mapper;

import com.example.todolist.application.dto.TodoStatusResponse;
import com.example.todolist.domain.model.TodoStatus;
import org.springframework.stereotype.Component;

@Component
public class TodoStatusResponseMapper {
    public TodoStatusResponse toResponse(TodoStatus todoStatus) {
        return new TodoStatusResponse(
                todoStatus.getId(),
                todoStatus.getTitle()
        );
    }
}
