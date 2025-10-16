package com.example.todolist.application.inbound.todo;

import com.example.todolist.application.dto.GetTodoDto;
import com.example.todolist.domain.model.Todo;

public interface GetTodo {
    Todo execute(GetTodoDto getTodoDto);
}
