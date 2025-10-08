package com.example.todolist.infrastructure.persistence.repository;

import com.example.todolist.infrastructure.persistence.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA репозиторий для CategoryEntity.
 */
public interface JpaCategoryRepository extends JpaRepository<CategoryEntity, Long> {

    /**
     * Найти не удалённую категорию по ID
     */
    @Query("SELECT c FROM CategoryEntity c WHERE c.id = :id AND c.deletedAt IS NULL")
    Optional<CategoryEntity> findByIdAndDeletedAtIsNull(@Param("id") Long id);

    /**
     * Найти все категории пользователя (без удалённых)
     */
    @Query("SELECT c FROM CategoryEntity c WHERE c.userId = :userId AND c.deletedAt IS NULL ORDER BY c.createdAt DESC")
    List<CategoryEntity> findByUserIdAndDeletedAtIsNull(@Param("userId") Long userId);

    /**
     * Найти все категории пользователя (включая удалённые)
     */
    @Query("SELECT c FROM CategoryEntity c WHERE c.userId = :userId ORDER BY c.createdAt DESC")
    List<CategoryEntity> findByUserId(@Param("userId") Long userId);
}
