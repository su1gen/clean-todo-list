package com.example.todolist.application.inbound.todo;

import com.example.todolist.domain.model.Todo;

import java.util.List;

public interface GetInboxTodos {
    List<Todo> execute(Long userId);
}
