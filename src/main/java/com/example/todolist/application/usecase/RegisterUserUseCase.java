package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.AuthResponse;
import com.example.todolist.application.dto.RegisterRequest;
import com.example.todolist.domain.exception.UserAlreadyExistsException;
import com.example.todolist.domain.model.Email;
import com.example.todolist.domain.model.Password;
import com.example.todolist.domain.model.User;
import com.example.todolist.domain.model.UserId;
import com.example.todolist.domain.repository.UserRepository;
import com.example.todolist.domain.service.PasswordEncoder;
import com.example.todolist.presentation.mapper.AuthResponseMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Use Case: Регистрация нового пользователя.
 * <p>
 * Бизнес-правила:
 * 1. Email должен быть уникальным
 * 2. Пароль должен быть >= 6 символов
 * 3. Пароль должен быть зашифрован перед сохранением
 */
@Component
class RegisterUserUseCase implements RegisterUser {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthResponseMapper authResponseMapper;

    RegisterUserUseCase(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthResponseMapper authResponseMapper
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authResponseMapper = authResponseMapper;
    }

    @Override
    public AuthResponse execute(RegisterRequest request) {
        // 1. Проверка уникальности email
        if (userRepository.existsByEmail(request.email())) {
            throw new UserAlreadyExistsException(request.email());
        }

        Password password = Password.fromPlainText(request.password());

        // 2. Шифрование пароля
        Password hashedPassword = passwordEncoder.encode(password);

        // 3. Создание доменной модели
        User user = new User(UserId.of(null), Email.of(request.email()), hashedPassword, LocalDateTime.now());

        // 4. Сохранение
        User savedUser = userRepository.save(user);

        // 5. Возврат ответа
        return authResponseMapper.toResponse(savedUser);
    }
}
