package com.example.todolist.infrastructure.persistence.repository;

import com.example.todolist.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Spring Data JPA репозиторий.
 * Работает с UserEntity (JPA).
 */
public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query(value = "SELECT nextval('users_id_seq')", nativeQuery = true)
    Long getNextUserId();
}
