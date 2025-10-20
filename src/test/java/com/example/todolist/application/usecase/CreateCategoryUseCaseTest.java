package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.CreateCategoryDto;
import com.example.todolist.application.outbound.category.CategoryNextIdExtractor;
import com.example.todolist.application.outbound.category.CategoryPersister;
import com.example.todolist.application.outbound.user.UserByIdExtractor;
import com.example.todolist.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateCategoryUseCaseTest {

    private CategoryPersister categoryPersister;
    private CategoryNextIdExtractor categoryNextIdExtractor;
    private UserByIdExtractor userByIdExtractor;
    private CreateCategoryUseCase createCategoryUseCase;

    @BeforeEach
    void setUp() {
        categoryPersister = mock(CategoryPersister.class);
        categoryNextIdExtractor = mock(CategoryNextIdExtractor.class);
        userByIdExtractor = mock(UserByIdExtractor.class);
        createCategoryUseCase = new CreateCategoryUseCase(
                categoryPersister,
                categoryNextIdExtractor,
                userByIdExtractor
        );
    }

    @Test
    void execute() {
        Long userId = 1L;
        Long nextCategoryId = 100L;
        User user = new User(UserId.of(userId), Email.of("test@example.com"), HashedPassword.of("qwerty"), LocalDateTime.now());
        CreateCategoryDto dto = new CreateCategoryDto("Work", userId);
        Category newCategory = new Category(
                CategoryId.of(nextCategoryId),
                Title.of("Work"),
                UserId.of(userId),
                LocalDateTime.now(),
                null
        );


        when(userByIdExtractor.findById(userId)).thenReturn(Optional.of(user));
        when(categoryNextIdExtractor.getNextCategoryId()).thenReturn(nextCategoryId);
        when(categoryPersister.persist(any(Category.class))).thenReturn(newCategory);


        Category result = createCategoryUseCase.execute(dto);

        verify(userByIdExtractor, times(1)).findById(userId);
        verify(categoryNextIdExtractor, times(1)).getNextCategoryId();
        verify(categoryPersister, times(1)).persist(any(Category.class));


        assertEquals(nextCategoryId, result.getId().getValue());
        assertEquals(dto.title(), result.getTitle().getValue());
        assertEquals(dto.userId(), result.getUserId().getValue());
        assertNotNull(result.getCreatedAt());
        assertNull(result.getDeletedAt());
    }
}