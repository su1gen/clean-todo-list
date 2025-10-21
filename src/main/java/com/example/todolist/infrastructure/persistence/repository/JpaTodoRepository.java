package com.example.todolist.infrastructure.persistence.repository;

import com.example.todolist.domain.model.TodoStatus;
import com.example.todolist.infrastructure.persistence.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface JpaTodoRepository extends JpaRepository<TodoEntity, Long> {
    /**
     * Найти не удалённую Todo по ID
     */
    @Query("SELECT t FROM TodoEntity t WHERE t.id = :id AND t.deletedAt IS NULL")
    Optional<TodoEntity> findByIdAndDeletedAtIsNull(@Param("id") Long id);

    @Query("SELECT t FROM TodoEntity t WHERE t.deletedAt IS NULL AND t.userId = :userId AND t.plannedAt BETWEEN :startOfDay AND :endOfDay ORDER BY t.id DESC")
    List<TodoEntity> findByDeletedAtIsNullAndPlannedAtBetweenOrderByIdDesc(
            @Param("userId") Long userId,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );

    @Query("SELECT t FROM TodoEntity t WHERE t.deletedAt IS NULL AND t.userId = :userId AND t.status = :todoStatus ORDER BY t.id DESC")
    List<TodoEntity> findByUserIdAndDeletedAtIsNullAndStatusOrderByIdDesc(
            @Param("userId") Long userId,
            @Param("todoStatus") TodoStatus todoStatus
    );

    @Query("SELECT t FROM TodoEntity t WHERE t.deletedAt IS NULL AND t.userId = :userId AND t.categoryId = :categoryId AND t.status = :todoStatus ORDER BY t.id DESC")
    List<TodoEntity> findByUserIdAndCategoryIdAndDeletedAtIsNullAndStatusOrderByIdDesc(
            @Param("userId") Long userId,
            @Param("categoryId") Long categoryId,
            @Param("todoStatus") TodoStatus todoStatus
    );

    @Query("SELECT t FROM TodoEntity t WHERE t.deletedAt IS NULL AND t.categoryId IS NULL AND t.userId = :userId AND t.status = :todoStatus ORDER BY t.id DESC")
    List<TodoEntity> findByUserIdAndCategoryIdIsNullAndDeletedAtIsNullAndStatusOrderByIdDesc(
            @Param("userId") Long userId,
            @Param("todoStatus") TodoStatus todoStatus
    );

    @Query(value = "SELECT nextval('todos_id_seq')", nativeQuery = true)
    Long getNextTodoId();
}
