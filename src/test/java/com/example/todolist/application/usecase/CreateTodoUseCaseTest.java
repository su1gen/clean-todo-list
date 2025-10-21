package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.CreateTodoDto;
import com.example.todolist.application.outbound.category.ActiveCategoryExtractor;
import com.example.todolist.application.outbound.todo.TodoNextIdExtractor;
import com.example.todolist.application.outbound.todo.TodoPersister;
import com.example.todolist.application.outbound.user.UserByIdExtractor;
import com.example.todolist.domain.exception.CategoryNotFoundException;
import com.example.todolist.domain.exception.UserNotFoundException;
import com.example.todolist.domain.model.*;
import com.example.todolist.domain.model.category.Category;
import com.example.todolist.domain.model.category.CategoryId;
import com.example.todolist.domain.model.category.TodoCategoryTitle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateTodoUseCaseTest {

    private TodoNextIdExtractor todoNextIdExtractor;
    private UserByIdExtractor userByIdExtractor;
    private ActiveCategoryExtractor activeCategoryExtractor;
    private TodoPersister todoPersister;
    private CreateTodoUseCase createTodoUseCase;

    @BeforeEach
    void setUp() {
        todoPersister = mock(TodoPersister.class);
        todoNextIdExtractor = mock(TodoNextIdExtractor.class);
        userByIdExtractor = mock(UserByIdExtractor.class);
        activeCategoryExtractor = mock(ActiveCategoryExtractor.class);
        createTodoUseCase = new CreateTodoUseCase(
                todoNextIdExtractor,
                userByIdExtractor,
                activeCategoryExtractor,
                todoPersister
        );
    }

    @Test
    void testCreateTodo() {
        Long nextTodoId = 100L;
        CreateTodoDto createTodoDto = new CreateTodoDto(
                "Todo title",
                "Todo description",
                1L,
                1L,
                LocalDateTime.now()
        );
        CategoryId categoryId = CategoryId.of(createTodoDto.categoryId());
        UserId userId = UserId.of(createTodoDto.userId());

        User user = new User(
                userId,
                Email.of("test@example.com"),
                HashedPassword.of("qwerty"),
                LocalDateTime.now()
        );
        Category category = new Category(
                categoryId,
                Title.of("Category title"),
                userId,
                LocalDateTime.now(),
                null
        );
        Todo todo = new Todo(
                TodoId.of(nextTodoId),
                Title.of(createTodoDto.title()),
                createTodoDto.description(),
                categoryId,
                TodoCategoryTitle.of(category.getTitle().getValue()),
                userId,
                TodoStatus.CREATED,
                LocalDateTime.now(),
                null,
                LocalDateTime.now()
        );

        when(userByIdExtractor.findById(createTodoDto.userId())).thenReturn(Optional.of(user));
        when(todoNextIdExtractor.getNextTodoId()).thenReturn(nextTodoId);
        when(activeCategoryExtractor.getActiveCategoryById(any(CategoryId.class))).thenReturn(Optional.of(category));
        when(todoPersister.persist(any(Todo.class))).thenReturn(todo);


        Todo result = createTodoUseCase.execute(createTodoDto);

        verify(userByIdExtractor, times(1)).findById(createTodoDto.userId());
        verify(todoNextIdExtractor, times(1)).getNextTodoId();
        verify(activeCategoryExtractor, times(1)).getActiveCategoryById(any(CategoryId.class));
        verify(todoPersister, times(1)).persist(any(Todo.class));


        assertEquals(nextTodoId, result.getId().getValue());
        assertEquals(createTodoDto.title(), result.getTitle().getValue());
        assertEquals(createTodoDto.description(), result.getDescription());
        assertEquals(createTodoDto.categoryId(), result.getCategoryId().getValue());
        assertEquals(category.getTitle().getValue(), result.getCategoryTitle().getValue());
        assertEquals(createTodoDto.userId(), result.getUserId().getValue());
        assertEquals(TodoStatus.CREATED.getId(), result.getStatus().getId());
        assertNotNull(result.getCreatedAt());
        assertNull(result.getDeletedAt());
        assertNotNull(result.getPlannedAt());
    }

    @Test
    void testThrowUserNotFoundException() {
        CreateTodoDto createTodoDto = new CreateTodoDto(
                "Todo title",
                "Todo description",
                1L,
                1L,
                LocalDateTime.now()
        );

        when(userByIdExtractor.findById(createTodoDto.userId())).thenReturn(Optional.empty());

        assertThrowsExactly(UserNotFoundException.class, () -> createTodoUseCase.execute(createTodoDto));

        verify(userByIdExtractor, times(1)).findById(createTodoDto.userId());
    }

    @Test
    void testThrowCategoryNotFoundException() {
        CreateTodoDto createTodoDto = new CreateTodoDto(
                "Todo title",
                "Todo description",
                1L,
                1L,
                LocalDateTime.now()
        );
        UserId userId = UserId.of(createTodoDto.userId());

        User user = new User(
                userId,
                Email.of("test@example.com"),
                HashedPassword.of("qwerty"),
                LocalDateTime.now()
        );

        when(userByIdExtractor.findById(createTodoDto.userId())).thenReturn(Optional.of(user));
        when(activeCategoryExtractor.getActiveCategoryById(any(CategoryId.class)))
                .thenThrow(new CategoryNotFoundException(createTodoDto.categoryId()));


        assertThrowsExactly(CategoryNotFoundException.class, () -> createTodoUseCase.execute(createTodoDto));

        verify(userByIdExtractor, times(1)).findById(createTodoDto.userId());
        verify(activeCategoryExtractor, times(1)).getActiveCategoryById(any(CategoryId.class));
    }
}