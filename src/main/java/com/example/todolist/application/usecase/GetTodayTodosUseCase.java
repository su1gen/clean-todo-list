package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.TodoWithCategoryDto;
import com.example.todolist.application.inbound.todo.GetTodayTodos;
import com.example.todolist.application.outbound.todo.TodayActiveTodosExtractor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class GetTodayTodosUseCase implements GetTodayTodos {
    private final TodayActiveTodosExtractor todayActiveTodosExtractor;

    GetTodayTodosUseCase(TodayActiveTodosExtractor todayActiveTodosExtractor) {
        this.todayActiveTodosExtractor = todayActiveTodosExtractor;
    }

    @Override
    public List<TodoWithCategoryDto> execute(Long userId) {
        return todayActiveTodosExtractor.getUserTodayTodos(userId)
                .stream()
                .map(TodoWithCategoryDto::from)
                .toList();
    }
}
