package com.example.todolist.presentation.controller;

import com.example.todolist.application.dto.CreateCategoryDto;
import com.example.todolist.domain.model.category.Category;
import com.example.todolist.presentation.webmodels.CategoryResponseWebModel;
import com.example.todolist.presentation.webmodels.CreateCategoryWebModel;
import com.example.todolist.application.inbound.category.CreateCategory;
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
    public ResponseEntity<CategoryResponseWebModel> createCategory(
            @RequestBody CreateCategoryWebModel createCategoryWebModel,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {

        CreateCategoryDto createCategoryDto = createCategoryWebModel.toDto(userDetails.getUserId());

        Category newCategory = createCategory.execute(createCategoryDto);

        CategoryResponseWebModel response = CategoryResponseWebModel.from(newCategory);

        return ResponseEntity
                .status(201)
                .body(response);
    }
}
