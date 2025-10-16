package com.example.todolist.presentation.mapper;

import com.example.todolist.presentation.webmodels.TodoResponseWebModel;
import com.example.todolist.presentation.webmodels.TodoStatusResponseWebModel;
import com.example.todolist.domain.model.Todo;
import org.springframework.stereotype.Component;

@Component
public class TodoResponseMapper {

    public TodoResponseWebModel toResponse(Todo todo) {
        return new TodoResponseWebModel(
                todo.getId().getValue(),
                todo.getTitle().getValue(),
                todo.getDescription(),
                todo.getCategoryId().getValue(),
                new TodoStatusResponseWebModel(
                        todo.getStatus().getId(),
                        todo.getStatus().getTitle()
                ),
                todo.getCreatedAt(),
                todo.getPlannedAt()
        );
    }
}
