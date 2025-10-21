package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.CategoryWithTodosDto;
import com.example.todolist.application.dto.GetCategoryTodosDto;
import com.example.todolist.application.outbound.category.ActiveCategoryExtractor;
import com.example.todolist.application.outbound.todo.ActiveTodosByStatusAndCategoryExtractor;
import com.example.todolist.domain.model.*;
import com.example.todolist.domain.model.category.Category;
import com.example.todolist.domain.model.category.CategoryId;
import com.example.todolist.domain.model.category.TodoCategoryTitle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GetCategoryTodosUseCaseTest {
    private ActiveTodosByStatusAndCategoryExtractor todosByStatusAndCategoryExtractor;
    private ActiveCategoryExtractor activeCategoryExtractor;
    private GetCategoryTodosUseCase getCategoryTodosUseCase;

    @BeforeEach
    void setUp() {
        todosByStatusAndCategoryExtractor = mock(ActiveTodosByStatusAndCategoryExtractor.class);
        activeCategoryExtractor = mock(ActiveCategoryExtractor.class);
        getCategoryTodosUseCase = new GetCategoryTodosUseCase(
                todosByStatusAndCategoryExtractor,
                activeCategoryExtractor
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

        when(activeCategoryExtractor.getActiveCategoryById(any(CategoryId.class))).thenReturn(Optional.of(category));
        when(todosByStatusAndCategoryExtractor.getUserTodosByCategoryAndStatus(
                getCategoryTodosDto.userId(),
                getCategoryTodosDto.categoryId(),
                TodoStatus.fromUrlParam(getCategoryTodosDto.status())
        )).thenReturn(todos);

        CategoryWithTodosDto result = getCategoryTodosUseCase.execute(getCategoryTodosDto);

        verify(activeCategoryExtractor, times(1)).getActiveCategoryById(any(CategoryId.class));
        verify(todosByStatusAndCategoryExtractor, times(1)).getUserTodosByCategoryAndStatus(
                getCategoryTodosDto.userId(),
                getCategoryTodosDto.categoryId(),
                TodoStatus.fromUrlParam(getCategoryTodosDto.status())
        );


        assertEquals(category.getId().getValue(), result.category().getId().getValue());
        assertEquals(category.getTitle().getValue(), result.category().getTitle().getValue());
        assertEquals(category.getUserId().getValue(), result.category().getUserId().getValue());
        assertNotNull(result.category().getCreatedAt());
        assertNull(result.category().getDeletedAt());

        assertEquals(todos.size(), result.todos().size());
        assertEquals(todos.get(0).getId().getValue(), result.todos().get(0).getId().getValue());
        assertEquals(todos.get(1).getId().getValue(), result.todos().get(1).getId().getValue());
    }
}