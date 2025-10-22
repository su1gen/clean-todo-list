package com.example.todolist.application.inbound.todo;

import com.example.todolist.application.dto.CreateTodoDto;
import com.example.todolist.domain.model.todo.Todo;

public interface CreateTodo {
    Todo execute(CreateTodoDto createTodoDto);
}
