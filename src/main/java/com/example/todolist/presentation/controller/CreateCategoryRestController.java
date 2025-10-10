package com.example.todolist.presentation.controller;

import com.example.todolist.application.dto.CategoryResponse;
import com.example.todolist.application.dto.CreateCategoryDto;
import com.example.todolist.application.usecase.CreateCategory;
import com.example.todolist.infrastructure.security.userdetails.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPaths.CATEGORIES)
class CreateCategoryRestController {
    private final CreateCategory createCategory;

    CreateCategoryRestController(CreateCategory createCategory) {
        this.createCategory = createCategory;
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(
            @RequestBody CreateCategoryDto createCategoryDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        var response = createCategory.execute(createCategoryDto, userDetails.getUserId());

        return ResponseEntity
                .status(201)
                .body(response);
    }
}
