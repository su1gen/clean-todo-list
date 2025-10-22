package com.example.todolist.application.usecase.user;

import com.example.todolist.application.inbound.user.RegisterUser;
import com.example.todolist.application.outbound.user.UserByEmailExister;
import com.example.todolist.application.outbound.user.UserNextIdExtractor;
import com.example.todolist.application.outbound.user.UserPersister;
import com.example.todolist.domain.exception.UserAlreadyExistsException;
import com.example.todolist.domain.model.user.*;
import com.example.todolist.domain.service.PasswordEncoder;
import com.example.todolist.presentation.webmodels.RegisterWebModel;
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

    private final UserPersister userPersister;
    private final UserByEmailExister userByEmailExister;
    private final UserNextIdExtractor userNextIdExtractor;
    private final PasswordEncoder passwordEncoder;

    RegisterUserUseCase(UserPersister userPersister, UserByEmailExister userByEmailExister, UserNextIdExtractor userNextIdExtractor, PasswordEncoder passwordEncoder) {
        this.userPersister = userPersister;
        this.userByEmailExister = userByEmailExister;
        this.userNextIdExtractor = userNextIdExtractor;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User execute(RegisterWebModel registerWebModel) {
        // 1. Проверка уникальности email
        if (userByEmailExister.existsByEmail(registerWebModel.email())) {
            throw new UserAlreadyExistsException(registerWebModel.email());
        }

        Password password = Password.fromPlainText(registerWebModel.password());

        // 2. Шифрование пароля
        HashedPassword hashedPassword = passwordEncoder.encode(password);

        // 2.5. Получаем новый ид пользователя
        Long nextUserId = userNextIdExtractor.getNextUserId();

        // 3. Создание доменной модели
        User user = new User(
                UserId.of(nextUserId),
                Email.of(registerWebModel.email()), hashedPassword, LocalDateTime.now()
        );

        // 4. Сохранение
        return userPersister.persist(user);
    }
}
