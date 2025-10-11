package com.example.todolist.infrastructure.persistence.repository;

import com.example.todolist.application.outport.UserRepository;
import com.example.todolist.domain.model.User;
import com.example.todolist.infrastructure.persistence.entity.UserEntity;
import com.example.todolist.infrastructure.persistence.mapper.UserMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaRepository;
    private final UserMapper mapper;

    UserRepositoryImpl(JpaUserRepository jpaRepository, UserMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public User save(User user) {
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
    public List<User> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(User user) {
        if (user.getId() != null) {
            jpaRepository.deleteById(user.getId());
        }
    }
}
