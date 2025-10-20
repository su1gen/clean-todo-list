package com.example.todolist.application.usecase;

import com.example.todolist.application.outbound.todo.TodayActiveTodosExtractor;
import com.example.todolist.domain.model.*;

import java.time.LocalDateTime;
import java.util.List;

class TodayActiveTodosExtractorMock implements TodayActiveTodosExtractor {
    @Override
    public List<Todo> getUserTodayTodos(Long userId) {
        return List.of(new Todo(
                        TodoId.of(1L),
                        Title.of("Mock Todo"),
                        "This is a mock todo",
                        CategoryId.of(10L),
                        TodoCategoryTitle.of("Mock CategoryTitle 1"),
                        UserId.of(userId),
                        TodoStatus.CONFIRMED,
                        LocalDateTime.now(),
                        null,
                        LocalDateTime.now())
//                new Todo(
//                        TodoId.of(2L),
//                        Title.of("Mock Todo 2"),
//                        "This is a mock todo 2",
//                        CategoryId.of(11L),
//                        TodoCategoryTitle.of("Mock CategoryTitle 2"),
//                        UserId.of(userId),
//                        TodoStatus.IN_PROCESS,
//                        LocalDateTime.now(),
//                        null,
//                        LocalDateTime.now()
//                )
        );
    }
}
