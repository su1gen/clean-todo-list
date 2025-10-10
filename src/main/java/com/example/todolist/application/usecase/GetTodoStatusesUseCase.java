package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.TodoStatusResponse;
import com.example.todolist.domain.model.TodoStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
class GetTodoStatusesUseCase implements GetTodoStatuses {
    @Override
    public List<TodoStatusResponse> execute() {
        return Arrays.stream(TodoStatus.values())
                .map(todoStatus -> new TodoStatusResponse(
                        todoStatus.getId(),
                        todoStatus.getTitle()
                ))
                .toList();
    }
}
