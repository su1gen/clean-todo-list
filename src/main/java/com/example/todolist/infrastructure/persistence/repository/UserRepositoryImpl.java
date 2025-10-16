package com.example.todolist.infrastructure.persistence.repository;

import com.example.todolist.application.outbound.user.*;
import com.example.todolist.domain.model.User;
import com.example.todolist.infrastructure.persistence.entity.UserEntity;
import com.example.todolist.infrastructure.persistence.mapper.UserMapper;
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
    private final UserMapper mapper;

    UserRepositoryImpl(JpaUserRepository jpaRepository, UserMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public User persist(User user) {
        UserEntity entity = mapper.toEntity(user);
        UserEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public User update(User user) {
        UserEntity entity = mapper.toEntity(user);
        UserEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
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
