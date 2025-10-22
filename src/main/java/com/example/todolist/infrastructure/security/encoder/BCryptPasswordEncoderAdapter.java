package com.example.todolist.infrastructure.security.encoder;

import com.example.todolist.domain.model.user.HashedPassword;
import com.example.todolist.domain.model.user.Password;
import com.example.todolist.domain.service.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Адаптер для BCryptPasswordEncoder.
 * Реализует доменный интерфейс через Spring Security.
 */
@Component
class BCryptPasswordEncoderAdapter implements PasswordEncoder {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public HashedPassword encode(Password rawPassword) {
        return HashedPassword.of(encoder.encode(rawPassword.getValue()));
    }

    @Override
    public boolean matches(Password rawPassword, HashedPassword encodedPassword) {
        System.out.println("RAW: " + rawPassword + " Hashed" + encodedPassword);
        return encoder.matches(rawPassword.getValue(), encodedPassword.hash());
    }
}
