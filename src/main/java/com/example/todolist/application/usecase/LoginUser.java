package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.LoginRequest;
import com.example.todolist.application.dto.TokenDto;

public interface LoginUser {
    TokenDto execute(LoginRequest request);
}
