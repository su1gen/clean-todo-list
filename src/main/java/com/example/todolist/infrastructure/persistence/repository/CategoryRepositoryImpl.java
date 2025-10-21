package com.example.todolist.infrastructure.persistence.repository;

import com.example.todolist.application.outbound.category.*;
import com.example.todolist.domain.exception.CategoryNotFoundException;
import com.example.todolist.domain.model.category.Category;
import com.example.todolist.domain.model.category.CategoryId;
import com.example.todolist.infrastructure.persistence.entity.CategoryEntity;
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
        CategoriesExtractor,
        CategoryExister
{

    private final JpaCategoryRepository jpaRepository;

    public CategoryRepositoryImpl(
            JpaCategoryRepository jpaRepository
    ) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Category persist(Category category) {
        CategoryEntity entity = CategoryEntity.fromDomain(category);
        CategoryEntity savedEntity = jpaRepository.save(entity);
        return CategoryEntity.toDomain(savedEntity);
    }

    @Override
    public Category update(Category category) {
        CategoryEntity entity = CategoryEntity.fromDomain(category);
        CategoryEntity savedEntity = jpaRepository.save(entity);
        return CategoryEntity.toDomain(savedEntity);
    }

    @Override
    public List<Category> getUserActiveCategories(Long userId) {
        List<CategoryEntity> categories = jpaRepository.findByUserIdAndDeletedAtIsNullOrderByIdDesc(userId);
        return categories
                .stream()
                .map(CategoryEntity::toDomain)
                .toList();
    }

    @Override
    public Optional<Category> getActiveCategoryById(CategoryId categoryId) {
        if (categoryId.notEmpty()) {
            return Optional.ofNullable(jpaRepository.findByIdAndDeletedAtIsNull(categoryId.getValue())
                    .map(CategoryEntity::toDomain)
                    .orElseThrow(() -> new CategoryNotFoundException(categoryId.getValue())));
        }
        return Optional.empty();
    }

    @Override
    public List<Category> getCategoriesByIds(Set<Long> ids) {
        return jpaRepository.findByIdsAndDeletedAtIsNull(ids)
                .stream()
                .map(CategoryEntity::toDomain)
                .toList();
    }

    @Override
    public CategoryId getNextCategoryId() {
        final var next = jpaRepository.getNextCategoryId();
        return CategoryId.of(next);
    }

    @Override
    public boolean exists(CategoryId categoryId) {
        if (!jpaRepository.existsById(categoryId.getValue())){
            throw new CategoryNotFoundException(categoryId.getValue());
        }
        return true;
    }
}
