package com.example.todolist.presentation.webmodels;

import com.example.todolist.domain.model.User;

/**
 * DTO для ответа после успешной аутентификации.
 * НЕ содержит сам токен (он будет в HttpOnly cookie).
 */
public record AuthResponseWebModel(
        Long userId,
        String email
) {
    public static AuthResponseWebModel from(User user) {
        return new AuthResponseWebModel(
                user.getId().getValue(),
                user.getEmail().getValue()
        );
    }
}
