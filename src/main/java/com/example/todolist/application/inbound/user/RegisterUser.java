package com.example.todolist.application.inbound.user;

import com.example.todolist.application.dto.AuthResponse;
import com.example.todolist.application.dto.RegisterRequest;

public interface RegisterUser {
    AuthResponse execute(RegisterRequest request);
}
