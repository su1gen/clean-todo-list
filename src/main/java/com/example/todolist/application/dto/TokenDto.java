package com.example.todolist.application.dto;

public record TokenDto(
        String token,
        Long expiresAt
) {
}
