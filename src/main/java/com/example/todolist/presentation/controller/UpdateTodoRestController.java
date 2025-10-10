package com.example.todolist.presentation.controller;

import com.example.todolist.application.dto.TodoResponse;
import com.example.todolist.application.dto.UpdateTodoDto;
import com.example.todolist.application.usecase.UpdateTodo;
import com.example.todolist.infrastructure.security.userdetails.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPaths.TODOS)
class UpdateTodoRestController {
    private final UpdateTodo updateTodo;

    UpdateTodoRestController(UpdateTodo updateTodo) {
        this.updateTodo = updateTodo;
    }


    @PutMapping("/{id}")
    public ResponseEntity<TodoResponse> editTodo(
            @PathVariable Long id,
            @RequestBody UpdateTodoDto updateTodoDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        var response = updateTodo.execute(id, updateTodoDto, userDetails.getUserId());
        return ResponseEntity.ok(response);
    }
}
