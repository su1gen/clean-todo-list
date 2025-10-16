package com.example.todolist.presentation.webmodels;

public record LoginWebModel(
//        @NotBlank(message = "Email не может быть пустым")
        String email,

//        @NotBlank(message = "Пароль не может быть пустым")
        String password
) {
}
