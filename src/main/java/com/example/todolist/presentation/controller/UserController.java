package com.example.todolist.presentation.controller;

import com.example.todolist.application.dto.UserResponse;
import com.example.todolist.application.usecase.GetUser;
import com.example.todolist.infrastructure.security.userdetails.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * REST контроллер для работы с пользователями.
 * Все эндпоинты требуют аутентификации.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final GetUser getUser;

    public UserController(GetUser getUser) {
        this.getUser = getUser;
    }

    /**
     * GET /api/users/me - Получить данные текущего пользователя
     *
     * @AuthenticationPrincipal автоматически извлекает UserDetails из SecurityContext
     */
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        UserResponse response = getUser.executeById(userDetails.getUserId());
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/users/{id} - Получить пользователя по ID
     * (пример защищённого эндпоинта)
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse response = getUser.executeById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/users/by-email?email=test@test.com
     */
    @GetMapping("/by-email")
    public ResponseEntity<UserResponse> getUserByEmail(@RequestParam String email) {
        UserResponse response = getUser.executeByEmail(email);
        return ResponseEntity.ok(response);
    }
}
