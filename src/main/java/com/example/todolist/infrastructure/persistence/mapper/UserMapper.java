package com.example.todolist.infrastructure.persistence.mapper;

import com.example.todolist.domain.model.User;
import com.example.todolist.infrastructure.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

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
                user.getEmail(),
                user.getPassword()
        );

        if (user.getId() != null) {
            entity.setId(user.getId());
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
                entity.getId(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getCreatedAt()
        );
    }
}
