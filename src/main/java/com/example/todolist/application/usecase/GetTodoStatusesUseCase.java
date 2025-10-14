package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.TodoStatusResponse;
import com.example.todolist.domain.model.TodoStatus;
import com.example.todolist.presentation.mapper.TodoStatusResponseMapper;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
class GetTodoStatusesUseCase implements GetTodoStatuses {
    private final TodoStatusResponseMapper todoStatusResponseMapper;

    GetTodoStatusesUseCase(TodoStatusResponseMapper todoStatusResponseMapper) {
        this.todoStatusResponseMapper = todoStatusResponseMapper;
    }

    @Override
    public List<TodoStatusResponse> execute() {
        return Arrays.stream(TodoStatus.values())
                .map(todoStatusResponseMapper::toResponse)
                .toList();
    }
}
