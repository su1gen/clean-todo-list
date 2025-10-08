package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.AuthResponse;
import com.example.todolist.application.dto.RegisterRequest;

public interface RegisterUser {
    AuthResponse execute(RegisterRequest request);
}
