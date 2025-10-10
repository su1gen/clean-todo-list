package com.example.todolist.presentation.controller;


import com.example.todolist.application.dto.CreateTodoDto;
import com.example.todolist.application.dto.TodoResponse;
import com.example.todolist.application.usecase.CreateTodo;
import com.example.todolist.infrastructure.security.userdetails.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPaths.TODOS)
class CreateTodoRestController {
    private final CreateTodo createTodo;

    CreateTodoRestController(CreateTodo createTodo) {
        this.createTodo = createTodo;
    }


    @PostMapping
    public ResponseEntity<TodoResponse> createTodo(
            @RequestBody CreateTodoDto createTodoDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        final TodoResponse createdTask = createTodo.execute(createTodoDto, userDetails.getUserId());

        return ResponseEntity.ok(createdTask);
    }
}
