package com.example.todolist.presentation.controller;


import com.example.todolist.domain.model.category.Category;
import com.example.todolist.presentation.webmodels.CategoryResponseWebModel;
import com.example.todolist.application.inbound.category.GetCategories;
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
    public ResponseEntity<List<CategoryResponseWebModel>> getCategories(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        List<Category> categories = getCategories.execute(userDetails.getUserId());

        List<CategoryResponseWebModel> response = categories
                .stream()
                .map(CategoryResponseWebModel::from)
                .toList();

        return ResponseEntity.ok(response);
    }
}
