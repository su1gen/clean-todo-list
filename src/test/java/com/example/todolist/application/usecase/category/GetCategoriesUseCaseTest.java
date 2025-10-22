package com.example.todolist.application.usecase.category;

import com.example.todolist.application.outbound.category.UserActiveCategoriesExtractor;
import com.example.todolist.domain.model.category.Category;
import com.example.todolist.domain.model.category.CategoryId;
import com.example.todolist.domain.model.Title;
import com.example.todolist.domain.model.user.UserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GetCategoriesUseCaseTest {

    private GetCategoriesUseCase getCategoriesUseCase;
    private UserActiveCategoriesExtractor userActiveCategoriesExtractor;

    @BeforeEach
    void setUp() {
        userActiveCategoriesExtractor = mock(UserActiveCategoriesExtractor.class);
        getCategoriesUseCase = new GetCategoriesUseCase(userActiveCategoriesExtractor);
    }


    @Test
    void shouldReturnNotEmptyList() {
        Long userId = 1L;
        var categoryList = List.of(
                new Category(CategoryId.of(1L), Title.of("1111"), UserId.of(1L), LocalDateTime.now(), null),
                new Category(CategoryId.of(2L), Title.of("2222"), UserId.of(1L), LocalDateTime.now(), null),
                new Category(CategoryId.of(3L), Title.of("3333"), UserId.of(1L), LocalDateTime.now(), null)
        );

        when(userActiveCategoriesExtractor.getUserActiveCategories(userId)).thenReturn(categoryList);

        List<Category> categories = getCategoriesUseCase.execute(userId);

        verify(userActiveCategoriesExtractor, times(1)).getUserActiveCategories(userId);
        assertEquals(3, categories.size());

    }

    @Test
    void shouldReturnEmptyList() {
        Long userId = 1L;

        when(userActiveCategoriesExtractor.getUserActiveCategories(userId)).thenReturn(List.of());

        List<Category> categories = getCategoriesUseCase.execute(userId);

        verify(userActiveCategoriesExtractor, times(1)).getUserActiveCategories(userId);
        assertEquals(0, categories.size());

    }
}