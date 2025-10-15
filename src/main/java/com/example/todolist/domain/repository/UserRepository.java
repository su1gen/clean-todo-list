package com.example.todolist.domain.repository;

import com.example.todolist.domain.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Порт (интерфейс) для работы с хранилищем пользователей.
 *
 * Это контракт между бизнес-логикой и внешним миром.
 * Реализация будет в infrastructure слое.
 *
 * Принципы:
 * - Оперирует доменными моделями (не JPA entities)
 * - Описывает ЧТО нужно, а не КАК это сделать
 * - Независим от Spring Data JPA
 */
public interface UserRepository {

    /**
     * Сохранить пользователя (создать или обновить)
     */
    User save(User user);

    /**
     * Найти по email
     */
    Optional<User> findByEmail(String email);

    /**
     * Найти по ID
     */
    Optional<User> findById(Long id);

    /**
     * Проверить существование по email
     */
    boolean existsByEmail(String email);

    /**
     * получить следующий ID
     */
    Long getNextUserId();
}
