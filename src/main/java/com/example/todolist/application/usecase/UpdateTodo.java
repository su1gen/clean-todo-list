package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.TodoResponse;
import com.example.todolist.application.dto.UpdateTodoDto;

public interface UpdateTodo {
    TodoResponse execute(Long todoId, UpdateTodoDto request, Long userId);
}
