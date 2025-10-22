package com.example.todolist.application.usecase.todo;

import com.example.todolist.application.dto.TodoStatusDto;
import com.example.todolist.application.inbound.todo.GetTodoStatuses;
import com.example.todolist.domain.model.todo.TodoStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
class GetTodoStatusesUseCase implements GetTodoStatuses {

    @Override
    public List<TodoStatusDto> execute() {
        return Arrays.stream(TodoStatus.values())
                .map(item -> new TodoStatusDto(
                        item.getId(),
                        item.getTitle()
                ))
                .toList();
    }
}
