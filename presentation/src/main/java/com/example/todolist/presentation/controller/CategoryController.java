package com.example.todolist.presentation.controller;

import com.example.todolist.application.dto.CategoryResponse;
import com.example.todolist.application.dto.CreateCategoryDto;
import com.example.todolist.application.dto.UpdateCategoryRequest;
import com.example.todolist.application.usecase.*;
import com.example.todolist.infrastructure.security.userdetails.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST контроллер для работы с категориями.
 * Все эндпоинты требуют аутентификации.
 * <p>
 * Эндпоинты:
 * - POST   /api/categories          - Создать категорию
 * - GET    /api/categories          - Получить все категории пользователя
 * - GET    /api/categories/{id}     - Получить категорию по ID
 * - PUT    /api/categories/{id}     - Обновить категорию
 * - DELETE /api/categories/{id}     - Удалить категорию (soft delete)
 * - POST   /api/categories/{id}/restore - Восстановить категорию
 */
@RestController
@RequestMapping(ApiPaths.CATEGORIES)
public class CategoryController {

    private final CreateCategory createCategoryUseCase;
    private final GetUserCategories getUserCategoriesUseCase;
    private final GetCategory getCategoryUseCase;
    private final UpdateCategory updateCategoryUseCase;
    private final DeleteCategory deleteCategoryUseCase;

    public CategoryController(
            CreateCategory createCategoryUseCase,
            GetUserCategories getUserCategories,
            GetCategory getCategoryUseCase,
            UpdateCategory updateCategoryUseCase,
            DeleteCategory deleteCategoryUseCase
    ) {
        this.createCategoryUseCase = createCategoryUseCase;
        this.getUserCategoriesUseCase = getUserCategories;
        this.getCategoryUseCase = getCategoryUseCase;
        this.updateCategoryUseCase = updateCategoryUseCase;
        this.deleteCategoryUseCase = deleteCategoryUseCase;
    }

    /**
     * POST /api/categories
     * Создать новую категорию для текущего пользователя
     */
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(
            @RequestBody CreateCategoryDto request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        CategoryResponse response = createCategoryUseCase.execute(
                request,
                userDetails.getUserId()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * GET /api/categories
     * Получить все категории текущего пользователя (без удалённых)
     * <p>
     * Query параметр: ?includeDeleted=true для получения всех категорий
     */
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getUserCategories(
            @RequestParam(required = false, defaultValue = "false") boolean includeDeleted,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        List<CategoryResponse> categories;

        if (includeDeleted) {
            categories = getUserCategoriesUseCase.executeIncludingDeleted(
                    userDetails.getUserId()
            );
        } else {
            categories = getUserCategoriesUseCase.execute(
                    userDetails.getUserId()
            );
        }

        return ResponseEntity.ok(categories);
    }

    /**
     * GET /api/categories/{id}
     * Получить категорию по ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        CategoryResponse response = getCategoryUseCase.execute(
                id,
                userDetails.getUserId()
        );
        return ResponseEntity.ok(response);
    }

    /**
     * PUT /api/categories/{id}
     * Обновить категорию
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable Long id,
            @RequestBody UpdateCategoryRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        CategoryResponse response = updateCategoryUseCase.execute(
                id,
                request,
                userDetails.getUserId()
        );
        return ResponseEntity.ok(response);
    }

    /**
     * DELETE /api/categories/{id}
     * Удалить категорию (soft delete)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        deleteCategoryUseCase.execute(id, userDetails.getUserId());
        return ResponseEntity.noContent().build();
    }

    /**
     * POST /api/categories/{id}/restore
     * Восстановить удалённую категорию
     */
    @PostMapping("/{id}/restore")
    public ResponseEntity<Void> restoreCategory(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        deleteCategoryUseCase.restore(id, userDetails.getUserId());
        return ResponseEntity.noContent().build();
    }
}
