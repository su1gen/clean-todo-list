package com.example.todolist.infrastructure.persistence.repository;

import com.example.todolist.infrastructure.persistence.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Spring Data JPA репозиторий для CategoryEntity.
 */
public interface JpaCategoryRepository extends JpaRepository<CategoryEntity, Long> {

    /**
     * Найти категории по ID пользователя
     */
    @Query("SELECT c FROM CategoryEntity c WHERE c.userId = :userId AND c.deletedAt IS NULL ORDER BY c.id DESC ")
    List<CategoryEntity> findByUserIdAndDeletedAtIsNullOrderByIdDesc(@Param("userId") Long userId);

    /**
     * Найти не удалённую категорию по ID
     */
    @Query("SELECT c FROM CategoryEntity c WHERE c.id = :id AND c.deletedAt IS NULL")
    Optional<CategoryEntity> findByIdAndDeletedAtIsNull(@Param("id") Long id);

    /**
     * Найти не удалённые категории по ID
     */
    @Query("SELECT c FROM CategoryEntity c WHERE c.id IN :ids AND c.deletedAt IS NULL")
    List<CategoryEntity> findByIdsAndDeletedAtIsNull(@Param("ids") Set<Long> ids);

    /**
     * получить следующий ID
     */
    @Query(value = "SELECT nextval('categories_id_seq')", nativeQuery = true)
    Long getNextCategoryId();
}
