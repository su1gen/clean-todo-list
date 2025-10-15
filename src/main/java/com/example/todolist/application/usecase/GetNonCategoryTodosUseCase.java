package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.TodoResponse;
import com.example.todolist.application.inbound.todo.GetNonCategoryTodos;
import com.example.todolist.domain.model.TodoStatus;
import com.example.todolist.application.outbound.TodoRepository;
import com.example.todolist.presentation.mapper.TodoResponseMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class GetNonCategoryTodosUseCase implements GetNonCategoryTodos {
    private final TodoRepository todoRepository;
    private final TodoResponseMapper todoResponseMapper;

    GetNonCategoryTodosUseCase(TodoRepository todoRepository, TodoResponseMapper todoResponseMapper) {
        this.todoRepository = todoRepository;
        this.todoResponseMapper = todoResponseMapper;
    }

    @Override
    public List<TodoResponse> execute(Long userId, String statusUrlParam) {
        var status = TodoStatus.fromUrlParam(statusUrlParam);

        var todos = todoRepository.findByUserIdAndCategoryIdAndDeletedAtIsNullAndStatusOrderByIdDesc(userId, null, status);

        return todos
                .stream()
                .map(todoResponseMapper::toResponse)
                .toList();
    }
}
