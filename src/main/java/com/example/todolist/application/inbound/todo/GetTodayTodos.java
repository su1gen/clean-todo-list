package com.example.todolist.application.inbound.todo;

import com.example.todolist.domain.model.todo.Todo;

import java.util.List;

public interface GetTodayTodos {
    List<Todo> execute(Long userId);
}
