package com.example.todolist.application.inbound.todo;

import com.example.todolist.application.dto.TodoResponse;

public interface GetTodo {
    TodoResponse execute(Long todoId, Long userId);
}
