package com.example.todolist.infrastructure.security.encoder;

import com.example.todolist.domain.model.Password;
import com.example.todolist.domain.service.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Адаптер для BCryptPasswordEncoder.
 * Реализует доменный интерфейс через Spring Security.
 */
@Component
public class BCryptPasswordEncoderAdapter implements PasswordEncoder {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public Password encode(Password rawPassword) {
        return Password.of(encoder.encode(rawPassword.getValue()));
    }

    @Override
    public boolean matches(Password rawPassword, Password encodedPassword) {
        return encoder.matches(rawPassword.getValue(), encodedPassword.getValue());
    }
}
