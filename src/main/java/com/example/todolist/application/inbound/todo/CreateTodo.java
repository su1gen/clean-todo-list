package com.example.todolist.application.inbound.todo;

import com.example.todolist.application.dto.CreateTodoDto;
import com.example.todolist.application.dto.TodoResponse;

public interface CreateTodo {
    TodoResponse execute(CreateTodoDto request, Long userId);
}
