package com.example.todolist.infrastructure.persistence.mapper;

import com.example.todolist.domain.model.Email;
import com.example.todolist.domain.model.HashedPassword;
import com.example.todolist.domain.model.User;
import com.example.todolist.domain.model.UserId;
import com.example.todolist.infrastructure.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper между доменной моделью и JPA entity.
 * <p>
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
                user.getPassword().hash()
        );

        if (user.getId().notEmpty()) {
            entity.setId(user.getId().getValue());
        }

        if (user.getCreatedAt()!=null) {
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
                HashedPassword.of(entity.getPassword()),
                entity.getCreatedAt()
        );
    }
}
