//package com.example.todolist.presentation.controller;
//
//import com.example.todolist.rest.validators.CategoryExists;
//import com.example.todolist.services.todo.GetCategoryTodos;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping(ApiPaths.TODOS)
//class GetCategoryTodosRestController {
//
//    private final GetCategoryTodos getCategoryTodos;
//
//    GetCategoryTodosRestController(GetCategoryTodos getCategoryTodos) {
//        this.getCategoryTodos = getCategoryTodos;
//    }
//
//    @GetMapping("/category/{categoryId}")
//    public ResponseEntity<List<ResponseTodoWebModel>> getCategoryTodos(
//            @PathVariable @CategoryExists Long categoryId,
//            @RequestParam(defaultValue = "") String status) {
//        var todos = getCategoryTodos.execute(categoryId, status)
//                .stream()
//                .map(ResponseTodoWebModel::fromEntity)
//                .toList();
//
//        return ResponseEntity.ok(todos);
//    }
//}
