package com.example.todolist.presentation.controller;

final class ApiPaths {

    static final String TODOS = "/api/todos";
    static final String CATEGORIES = "/api/categories";
    static final String AUTH = "/api/auth";

    private ApiPaths() {
        throw new IllegalStateException("You can't create instance of  utility/helper class ");
    }
}
