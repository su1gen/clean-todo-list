package com.example.todolist.application.inbound.todo;

import com.example.todolist.application.dto.TodoWithCategoryDto;
import com.example.todolist.domain.model.Todo;

import java.util.List;

public interface GetInboxTodos {
    List<TodoWithCategoryDto> execute(Long userId);
}
