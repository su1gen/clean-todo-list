package com.example.todolist.application.usecase;

import com.example.todolist.application.inbound.todo.GetTodayTodos;
import com.example.todolist.application.outbound.todo.TodayActiveTodosExtractor;
import com.example.todolist.domain.model.Todo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class GetTodayTodosUseCase implements GetTodayTodos {
    private final TodayActiveTodosExtractor todayActiveTodosExtractor;

    GetTodayTodosUseCase(TodayActiveTodosExtractor todayActiveTodosExtractor) {
        this.todayActiveTodosExtractor = todayActiveTodosExtractor;
    }

    @Override
    public List<Todo> execute(Long userId) {
        return todayActiveTodosExtractor.getUserTodayTodos(userId);
    }
}
