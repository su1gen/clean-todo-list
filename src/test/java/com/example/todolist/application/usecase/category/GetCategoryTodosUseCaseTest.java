package com.example.todolist.application.usecase.category;

import com.example.todolist.application.dto.GetCategoryTodosDto;
import com.example.todolist.application.outbound.category.CategoryExister;
import com.example.todolist.application.outbound.todo.ActiveTodosByStatusAndCategoryExtractor;
import com.example.todolist.domain.model.*;
import com.example.todolist.domain.model.category.Category;
import com.example.todolist.domain.model.category.CategoryId;
import com.example.todolist.domain.model.category.TodoCategoryTitle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GetCategoryTodosUseCaseTest {
    private ActiveTodosByStatusAndCategoryExtractor todosByStatusAndCategoryExtractor;
    private CategoryExister categoryExister;
    private GetCategoryTodosUseCase getCategoryTodosUseCase;

    @BeforeEach
    void setUp() {
        todosByStatusAndCategoryExtractor = mock(ActiveTodosByStatusAndCategoryExtractor.class);
        categoryExister = mock(CategoryExister.class);
        getCategoryTodosUseCase = new GetCategoryTodosUseCase(
                todosByStatusAndCategoryExtractor,
                categoryExister
        );
    }

    @Test
    void testGetCategoryTodos() {
        GetCategoryTodosDto getCategoryTodosDto = new GetCategoryTodosDto(
                TodoStatus.IN_PROCESS.getUrlParam(),
                10L,
                20L
        );
        CategoryId categoryId = CategoryId.of(getCategoryTodosDto.categoryId());
        UserId userId = UserId.of(getCategoryTodosDto.userId());
        Category category = new Category(
                categoryId,
                Title.of("Category title"),
                userId,
                LocalDateTime.now(),
                null
        );
        List<Todo> todos = List.of(
                new Todo(
                        TodoId.of(1L),
                        Title.of("Mock Todo"),
                        "This is a mock todo",
                        categoryId,
                        TodoCategoryTitle.of("Mock CategoryTitle 1"),
                        userId,
                        TodoStatus.CONFIRMED,
                        LocalDateTime.now(),
                        null,
                        LocalDateTime.now()),
                new Todo(
                        TodoId.of(2L),
                        Title.of("Mock Todo 2"),
                        "This is a mock todo 2",
                        categoryId,
                        TodoCategoryTitle.of("Mock CategoryTitle 2"),
                        userId,
                        TodoStatus.IN_PROCESS,
                        LocalDateTime.now(),
                        null,
                        LocalDateTime.now()
                )
        );

        when(categoryExister.exists(any(CategoryId.class))).thenReturn(true);
        when(todosByStatusAndCategoryExtractor.getUserTodosByCategoryAndStatus(
                getCategoryTodosDto.userId(),
                getCategoryTodosDto.categoryId(),
                TodoStatus.fromUrlParam(getCategoryTodosDto.status())
        )).thenReturn(todos);

        List<Todo> result = getCategoryTodosUseCase.execute(getCategoryTodosDto);

        verify(categoryExister, times(1)).exists(any(CategoryId.class));
        verify(todosByStatusAndCategoryExtractor, times(1)).getUserTodosByCategoryAndStatus(
                getCategoryTodosDto.userId(),
                getCategoryTodosDto.categoryId(),
                TodoStatus.fromUrlParam(getCategoryTodosDto.status())
        );

        assertEquals(todos.size(), result.size());
        assertEquals(todos.get(0).getId().getValue(), result.get(0).getId().getValue());
        assertEquals(todos.get(1).getId().getValue(), result.get(1).getId().getValue());
    }
}