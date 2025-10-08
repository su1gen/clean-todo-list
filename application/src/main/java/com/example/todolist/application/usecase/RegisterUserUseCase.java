package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.AuthResponse;
import com.example.todolist.application.dto.RegisterRequest;
import com.example.todolist.domain.exception.UserAlreadyExistsException;
import com.example.todolist.domain.model.User;
import com.example.todolist.domain.repository.UserRepository;
import com.example.todolist.domain.service.PasswordEncoder;
import jakarta.inject.Named;

/**
 * Use Case: Регистрация нового пользователя.
 * <p>
 * Бизнес-правила:
 * 1. Email должен быть уникальным
 * 2. Пароль должен быть >= 6 символов
 * 3. Пароль должен быть зашифрован перед сохранением
 */
@Named
 class RegisterUserUseCase implements RegisterUser{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

     RegisterUserUseCase(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthResponse execute(RegisterRequest request) {
        // 1. Проверка уникальности email
        if (userRepository.existsByEmail(request.email())) {
            throw new UserAlreadyExistsException(request.email());
        }

        // 2. Шифрование пароля
        String hashedPassword = passwordEncoder.encode(request.password());

        // 3. Создание доменной модели
        User user = new User(request.email(), hashedPassword);

        // 4. Сохранение
        User savedUser = userRepository.save(user);

        // 5. Возврат ответа
        return new AuthResponse(
                savedUser.getId(),
                savedUser.getEmail()
        );
    }
}
