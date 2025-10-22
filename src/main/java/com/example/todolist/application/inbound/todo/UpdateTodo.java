package com.example.todolist.application.inbound.todo;

import com.example.todolist.application.dto.UpdateTodoDto;
import com.example.todolist.domain.model.todo.Todo;

public interface UpdateTodo {
    Todo execute(UpdateTodoDto updateTodoDto);
}
