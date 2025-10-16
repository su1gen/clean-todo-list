package com.example.todolist.application.inbound.todo;

import com.example.todolist.application.dto.TodoWithCategoryDto;

import java.util.List;

public interface GetTodayTodos {
    List<TodoWithCategoryDto> execute(Long userId);
}
