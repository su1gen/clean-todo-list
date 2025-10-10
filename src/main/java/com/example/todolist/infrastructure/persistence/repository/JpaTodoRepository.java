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
    List<TodoEntity> findByDeletedAtIsNullAndStatusOrderByIdDesc(
            @Param("userId") Long userId,
            @Param("todoStatus") TodoStatus todoStatus
    );

//    /**
//     * Найти все Todo пользователя (без удалённых)
//     */
//    @Query("SELECT t FROM TodoEntity t WHERE t.userId = :userId AND t.deletedAt IS NULL ORDER BY t.createdAt DESC")
//    List<TodoEntity> findByUserIdAndDeletedAtIsNull(@Param("userId") Long userId);
//
//    /**
//     * Найти Todo пользователя с фильтрацией по статусу
//     */
//    @Query("SELECT t FROM TodoEntity t WHERE t.userId = :userId AND t.status = :status AND t.deletedAt IS NULL ORDER BY t.createdAt DESC")
//    List<TodoEntity> findByUserIdAndStatusAndDeletedAtIsNull(
//            @Param("userId") Long userId,
//            @Param("status") TodoStatus status
//    );
//
//    /**
//     * Найти Todo пользователя по категории
//     */
//    @Query("SELECT t FROM TodoEntity t WHERE t.userId = :userId AND t.categoryId = :categoryId AND t.deletedAt IS NULL ORDER BY t.createdAt DESC")
//    List<TodoEntity> findByUserIdAndCategoryIdAndDeletedAtIsNull(
//            @Param("userId") Long userId,
//            @Param("categoryId") Long categoryId
//    );
//
//    /**
//     * Найти просроченные Todo пользователя
//     */
//    @Query("SELECT t FROM TodoEntity t WHERE t.userId = :userId " +
//            "AND t.plannedAt < :now " +
//            "AND t.status NOT IN (:finishedStatus, :notRelevantStatus) " +
//            "AND t.deletedAt IS NULL " +
//            "ORDER BY t.plannedAt ASC")
//    List<TodoEntity> findOverdueTodosByUserId(
//            @Param("userId") Long userId,
//            @Param("now") LocalDateTime now,
//            @Param("finishedStatus") TodoStatus finishedStatus,
//            @Param("notRelevantStatus") TodoStatus notRelevantStatus
//    );
//
//    /**
//     * Найти запланированные на сегодня Todo
//     */
//    @Query("SELECT t FROM TodoEntity t WHERE t.userId = :userId " +
//            "AND t.plannedAt BETWEEN :startOfDay AND :endOfDay " +
//            "AND t.deletedAt IS NULL " +
//            "ORDER BY t.plannedAt ASC")
//    List<TodoEntity> findTodayTodosByUserId(
//            @Param("userId") Long userId,
//            @Param("startOfDay") LocalDateTime startOfDay,
//            @Param("endOfDay") LocalDateTime endOfDay
//    );
}
