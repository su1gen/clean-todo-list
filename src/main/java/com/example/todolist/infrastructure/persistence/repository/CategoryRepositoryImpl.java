package com.example.todolist.infrastructure.persistence.repository;

import com.example.todolist.application.outbound.category.*;
import com.example.todolist.domain.model.Category;
import com.example.todolist.infrastructure.persistence.entity.CategoryEntity;
import com.example.todolist.infrastructure.persistence.mapper.CategoryMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Реализация доменного репозитория через JPA.
 */
@Component
public class CategoryRepositoryImpl implements
        ActiveCategoryExtractor,
        CategoryPersister,
        CategoryUpdater,
        CategoryNextIdExtractor,
        UserActiveCategoriesExtractor,
        CategoriesExtractor {

    private final JpaCategoryRepository jpaRepository;
    private final CategoryMapper mapper;

    public CategoryRepositoryImpl(
            JpaCategoryRepository jpaRepository,
            CategoryMapper mapper
    ) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Category persist(Category category) {
        CategoryEntity entity = mapper.toEntity(category);
        CategoryEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Category update(Category category) {
        CategoryEntity entity = mapper.toEntity(category);
        CategoryEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public List<Category> getUserActiveCategories(Long userId) {
        List<CategoryEntity> categories = jpaRepository.findByUserIdAndDeletedAtIsNullOrderByIdDesc(userId);
        return categories
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Category> getActiveCategoryById(Long id) {
        return jpaRepository.findByIdAndDeletedAtIsNull(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Category> getCategoriesByIds(Set<Long> ids) {
        return jpaRepository.findByIdsAndDeletedAtIsNull(ids)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Long getNextCategoryId() {
        return jpaRepository.getNextCategoryId();
    }
}
