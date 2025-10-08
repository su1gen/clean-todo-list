package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.AuthResponse;
import com.example.todolist.application.dto.LoginRequest;

public interface LoginUser {
    AuthResponse execute(LoginRequest request);
}
