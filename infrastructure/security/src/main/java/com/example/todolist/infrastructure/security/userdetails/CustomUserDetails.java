package com.example.todolist.infrastructure.security.userdetails;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Кастомная реализация UserDetails с дополнительным полем userId.
 *
 * Spring Security работает с username, но нам нужен доступ к userId.
 */
public class CustomUserDetails implements UserDetails {

    private final Long userId;
    private final String email; // username в терминах Spring Security
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(
            Long userId,
            String email,
            String password,
            Collection<? extends GrantedAuthority> authorities
    ) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    // Кастомное поле
    public Long getUserId() {
        return userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email; // username = email
    }

    @Override
    public String toString() {
        return "CustomUserDetails{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", authorities=" + authorities +
                '}';
    }
}
