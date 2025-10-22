package com.example.todolist.presentation.controller;


import com.example.todolist.application.dto.CreateTodoDto;
import com.example.todolist.domain.model.todo.Todo;
import com.example.todolist.presentation.webmodels.CreateTodoWebModel;
import com.example.todolist.presentation.webmodels.TodoResponseWebModel;
import com.example.todolist.application.inbound.todo.CreateTodo;
import com.example.todolist.infrastructure.security.userdetails.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<TodoResponseWebModel> createTodo(
            @RequestBody CreateTodoWebModel createTodoWebModel,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        CreateTodoDto createTodoDto = createTodoWebModel.toDto(userDetails.getUserId());

        Todo createdTodo = createTodo.execute(createTodoDto);

        final TodoResponseWebModel response = TodoResponseWebModel.from(createdTodo);

        return ResponseEntity.ok(response);
    }
}
