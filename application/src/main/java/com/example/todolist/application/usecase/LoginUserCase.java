package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.AuthResponse;
import com.example.todolist.application.dto.LoginRequest;
import com.example.todolist.domain.exception.InvalidCredentialsException;
import com.example.todolist.domain.exception.UserNotFoundException;
import com.example.todolist.domain.model.User;
import com.example.todolist.domain.repository.UserRepository;
import com.example.todolist.domain.service.PasswordEncoder;
import jakarta.inject.Named;

/**
 * Use Case: Аутентификация пользователя.
 * <p>
 * Бизнес-правила:
 * 1. Пользователь должен существовать
 * 2. Пароль должен совпадать с хешем
 */
@Named
 class LoginUserCase implements LoginUser {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

     LoginUserCase(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthResponse execute(LoginRequest request) {
        // 1. Найти пользователя по email
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UserNotFoundException(request.email()));

        // 2. Проверить пароль
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            // Важно: не раскрываем, что именно неверно (email или пароль)
            throw new InvalidCredentialsException();
        }

        // 3. Успешная аутентификация
        return new AuthResponse(
                user.getId(),
                user.getEmail()
        );
    }
}
