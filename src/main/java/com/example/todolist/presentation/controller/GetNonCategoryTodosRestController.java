//package com.example.todolist.presentation.controller;
//
//import com.example.todolist.services.todo.GetNonCategoryTodos;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping(ApiPaths.TODOS)
//class GetNonCategoryTodosRestController {
//    private final GetNonCategoryTodos getNonCategoryTodos;
//
//    GetNonCategoryTodosRestController(GetNonCategoryTodos getNonCategoryTodos) {
//        this.getNonCategoryTodos = getNonCategoryTodos;
//    }
//
//    @GetMapping("/without-category")
//    public ResponseEntity<List<ResponseTodoWebModel>> getWithoutCategoryTodos(
//            @RequestParam(defaultValue = "") String status) {
//        var todos = getNonCategoryTodos.execute(status)
//                .stream()
//                .map(ResponseTodoWebModel::fromEntity)
//                .toList();
//
//        return ResponseEntity.ok(todos);
//    }
//}
