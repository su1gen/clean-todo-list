package com.example.todolist.presentation.controller;


import com.example.todolist.application.dto.CategoryResponse;
import com.example.todolist.application.usecase.GetCategories;
import com.example.todolist.infrastructure.security.userdetails.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.CATEGORIES)
class GetCategoriesRestController {
    private final GetCategories getCategories;

    GetCategoriesRestController(GetCategories getCategories) {
        this.getCategories = getCategories;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getCategories(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        var categories = getCategories.execute(userDetails.getUserId());

        return ResponseEntity.ok(categories);
    }
}
