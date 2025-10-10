//package com.example.todolist.presentation.controller;
//
//import com.example.todolist.application.usecase.GetTodo;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping(ApiPaths.TODOS)
//class GetTodoByIdRestController {
//    private final GetTodo getTodo;
//
//    GetTodoByIdRestController(GetTodo getTodo) {
//        this.getTodo = getTodo;
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<ResponseTodoWebModel> getTodo(@PathVariable Long id) {
//        final var webModel = ResponseTodoWebModel.fromEntity(getTodoById.execute(id));
//
//        return ResponseEntity.ok(webModel);
//    }
//}
