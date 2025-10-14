package com.example.todolist.infrastructure.persistence.mapper;

import com.example.todolist.domain.model.Email;
import com.example.todolist.domain.model.Password;
import com.example.todolist.domain.model.User;
import com.example.todolist.domain.model.UserId;
import com.example.todolist.infrastructure.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Mapper между доменной моделью и JPA entity.
 *
 * Принцип: изоляция преобразований в одном месте.
 */
@Component
public class UserMapper {

    /**
     * Доменная модель → JPA Entity
     */
    public UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity(
                user.getEmail().getValue(),
                user.getPassword().getValue()
        );

        if (user.getId().notEmpty()) {
            entity.setId(user.getId().getValue());
        }

        if (user.getCreatedAt() != null) {
            entity.setCreatedAt(user.getCreatedAt());
        }

        return entity;
    }

    /**
     * JPA Entity → Доменная модель
     */
    public User toDomain(UserEntity entity) {
        return new User(
                UserId.of(entity.getId()),
                Email.of(entity.getEmail()),
                Password.of(entity.getPassword()),
                entity.getCreatedAt()
        );
    }
}
