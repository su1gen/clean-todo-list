package com.example.todolist.infrastructure.persistence.repository;

import com.example.todolist.application.outbound.user.*;
import com.example.todolist.domain.model.user.User;
import com.example.todolist.infrastructure.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Реализация доменного репозитория через JPA.
 * Это адаптер: переводит вызовы из domain слоя в JPA.
 * <p>
 * Преобразования:
 * Domain User → UserEntity → БД
 * БД → UserEntity → Domain User
 * <p>
 * Это адаптер, который связывает бизнес-логику (domain) с технологией (JPA)
 */
@Component
class UserRepositoryImpl implements
        UserPersister,
        UserUpdater,
        UserByIdExtractor,
        UserByEmailExtractor,
        UserByEmailExister,
        UserNextIdExtractor {

    private final JpaUserRepository jpaRepository;

    UserRepositoryImpl(JpaUserRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public User persist(User user) {
        UserEntity entity = UserEntity.fromDomain(user);
        UserEntity savedEntity = jpaRepository.save(entity);
        return UserEntity.toDomain(savedEntity);
    }

    @Override
    public User update(User user) {
        UserEntity entity = UserEntity.fromDomain(user);
        UserEntity savedEntity = jpaRepository.save(entity);
        return UserEntity.toDomain(savedEntity);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(UserEntity::toDomain);
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaRepository.findById(id)
                .map(UserEntity::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public Long getNextUserId() {
        return jpaRepository.getNextUserId();
    }
}
