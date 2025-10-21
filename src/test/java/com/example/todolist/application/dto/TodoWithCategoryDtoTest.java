package com.example.todolist.application.dto;

import com.example.todolist.domain.model.*;
import com.example.todolist.domain.model.category.CategoryId;
import com.example.todolist.domain.model.category.TodoCategoryTitle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class TodoWithCategoryDtoTest {

    @Test
    void from() {
        var todo = new Todo(
                TodoId.of(1L),
                Title.of("Mock Todo"),
                "This is a mock todo",
                CategoryId.of(10L),
                TodoCategoryTitle.of("Mock CategoryTitle 1"),
                UserId.of(20L),
                TodoStatus.CONFIRMED,
                LocalDateTime.now(),
                null,
                LocalDateTime.now());

        var dto = TodoWithCategoryDto.from(todo);

        Assertions.assertEquals(todo.getId().getValue(), dto.id());
        Assertions.assertEquals(todo.getTitle().getValue(), dto.title());
        Assertions.assertEquals(todo.getDescription(), dto.description());
        Assertions.assertEquals(todo.getCategoryTitle().getValue(), dto.categoryTitle());
        Assertions.assertEquals(todo.getStatus(), dto.status());
        Assertions.assertEquals(todo.getCreatedAt(), dto.createdAt());
        Assertions.assertEquals(todo.getPlannedAt(), dto.plannedAt());
    }
}