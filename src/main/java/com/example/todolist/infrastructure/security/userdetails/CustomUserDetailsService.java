package com.example.todolist.infrastructure.security.userdetails;

import com.example.todolist.application.outbound.user.UserByEmailExtractor;
import com.example.todolist.domain.model.user.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для загрузки пользователя для Spring Security.
 *
 * Адаптер между доменным UserRepository и Spring Security.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserByEmailExtractor userByEmailExtractor;

    public CustomUserDetailsService(UserByEmailExtractor userByEmailExtractor) {
        this.userByEmailExtractor = userByEmailExtractor;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Загружаем доменную модель
        User user = userByEmailExtractor.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found: " + email)
                );

        // Преобразуем в Spring Security UserDetails
        return new CustomUserDetails(
                user.getId().getValue(),
                user.getEmail().getValue(),
                user.getPassword().hash(),
                List.of() // Пока нет ролей, позже добавим
        );
    }
}
