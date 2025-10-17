package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.TodoWithCategoryDto;
import com.example.todolist.application.inbound.todo.GetTodayTodos;
import com.example.todolist.application.outbound.category.CategoriesExtractor;
import com.example.todolist.application.outbound.todo.TodayActiveTodosExtractor;
import com.example.todolist.domain.model.Category;
import com.example.todolist.domain.model.Todo;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
class GetTodayTodosUseCase implements GetTodayTodos {
    private final TodayActiveTodosExtractor todayActiveTodosExtractor;
    private final CategoriesExtractor categoriesExtractor;

    GetTodayTodosUseCase(TodayActiveTodosExtractor todayActiveTodosExtractor, CategoriesExtractor categoriesExtractor) {
        this.todayActiveTodosExtractor = todayActiveTodosExtractor;
        this.categoriesExtractor = categoriesExtractor;
    }

    @Override
    public List<TodoWithCategoryDto> execute(Long userId) {
        List<Todo> todos = todayActiveTodosExtractor.getUserTodayTodos(userId);

        return todos
                .stream()
                .map(todo -> new TodoWithCategoryDto(
                        todo.getId().getValue(),
                        todo.getTitle().getValue(),
                        todo.getDescription(),
                        todo.getCategoryTitle().getValue(),
                        todo.getStatus(),
                        todo.getCreatedAt(),
                        todo.getPlannedAt()
                ))
                .toList();
    }
}
