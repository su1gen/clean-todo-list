package com.example.todolist.presentation.mapper;

import com.example.todolist.presentation.webmodels.TodoStatusResponseWebModel;
import com.example.todolist.domain.model.TodoStatus;
import org.springframework.stereotype.Component;

@Component
public class TodoStatusResponseMapper {
    public TodoStatusResponseWebModel toResponse(TodoStatus todoStatus) {
        return new TodoStatusResponseWebModel(
                todoStatus.getId(),
                todoStatus.getTitle()
        );
    }
}
