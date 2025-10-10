package com.example.todolist.infrastructure.persistence.repository;

import com.example.todolist.domain.model.Category;
import com.example.todolist.domain.repository.CategoryRepository;
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
public class CategoryRepositoryImpl implements CategoryRepository {

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
    public Category save(Category category) {
        CategoryEntity entity = mapper.toEntity(category);
        CategoryEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public List<Category> findByUserIdAndNotDeletedWithDescOrder(Long userId) {
        List<CategoryEntity> categories = jpaRepository.findByUserIdAndDeletedAtIsNullOrderByIdDesc(userId);
        return categories
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Category> findByIdAndNotDeleted(Long id) {
        return jpaRepository.findByIdAndDeletedAtIsNull(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Category> findByIdsAndDeletedAtIsNull(Set<Long> ids) {
        return jpaRepository.findByIdsAndDeletedAtIsNull(ids)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
