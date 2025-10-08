package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.TodoResponse;
import com.example.todolist.application.dto.UpdateTodoRequest;

public interface UpdateTodo {
    TodoResponse execute(Long todoId, UpdateTodoRequest request, Long userId);
}
