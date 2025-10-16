package com.example.todolist.application.inbound.todo;

import com.example.todolist.application.dto.TodoStatusDto;

import java.util.List;

public interface GetTodoStatuses {
    List<TodoStatusDto> execute();
}
