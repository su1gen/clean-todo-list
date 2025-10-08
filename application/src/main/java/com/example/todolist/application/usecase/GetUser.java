package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.UserResponse;

public interface GetUser {
    UserResponse executeById(Long id);

    UserResponse executeByEmail(String email);
}
