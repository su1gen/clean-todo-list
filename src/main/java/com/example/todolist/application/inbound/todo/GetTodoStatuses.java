package com.example.todolist.application.inbound.todo;

import com.example.todolist.application.dto.TodoStatusResponse;

import java.util.List;

public interface GetTodoStatuses {
    List<TodoStatusResponse> execute();
}
