package com.example.todolist.presentation.mapper;

import com.example.todolist.application.dto.AuthResponse;
import com.example.todolist.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class AuthResponseMapper {
    public AuthResponse toResponse(User user) {
        return new AuthResponse(
                user.getId().getValue(),
                user.getEmail().getValue()
        );
    }
}
