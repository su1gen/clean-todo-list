package com.example.todolist.infrastructure.persistence.repository;

import com.example.todolist.domain.model.Category;
import com.example.todolist.domain.repository.CategoryRepository;
import com.example.todolist.infrastructure.persistence.entity.CategoryEntity;
import com.example.todolist.infrastructure.persistence.mapper.CategoryMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Реализация доменного репозитория через JPA.
 */
@Component
 class CategoryRepositoryImpl implements CategoryRepository {

    private final JpaCategoryRepository jpaRepository;
    private final CategoryMapper mapper;

     CategoryRepositoryImpl(
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
    public Optional<Category> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Category> findByIdAndNotDeleted(Long id) {
        return jpaRepository.findByIdAndDeletedAtIsNull(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Category> findByUserIdAndNotDeleted(Long userId) {
        return jpaRepository.findByUserIdAndDeletedAtIsNull(userId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Category> findByUserId(Long userId) {
        return jpaRepository.findByUserId(userId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
