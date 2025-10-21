package com.example.todolist.presentation.controller;

import com.example.todolist.application.dto.category.RenameCategoryDto;
import com.example.todolist.application.inbound.category.RenameCategory;
import com.example.todolist.infrastructure.security.userdetails.CustomUserDetails;
import com.example.todolist.presentation.webmodels.RenameCategoryWebModel;
import com.example.todolist.presentation.webmodels.TodoResponseWebModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPaths.CATEGORIES)
class RenameCategoryRestController {
    private final RenameCategory renameCategory;

    RenameCategoryRestController(RenameCategory renameCategory) {
        this.renameCategory = renameCategory;
    }


    @PutMapping("/{categoryId}")
    public ResponseEntity<TodoResponseWebModel> editTodo(
            @PathVariable Long categoryId,
            @RequestBody RenameCategoryWebModel renameCategoryWebModel,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        RenameCategoryDto renameCategoryDto = renameCategoryWebModel.toDto(categoryId, userDetails.getUserId());

        renameCategory.execute(renameCategoryDto);

        return ResponseEntity.ok().build();
    }
}
