package com.example.todolist.application.dto;



public record CreateCategoryDto(
//        @NotBlank(message = "Название категории не может быть пустым")
//        @Size(max = 255, message = "Название не может быть длиннее 255 символов")
        String title
) {
}
