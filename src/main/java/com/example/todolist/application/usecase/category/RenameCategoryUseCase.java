package com.example.todolist.application.usecase.category;

import com.example.todolist.application.dto.category.RenameCategoryDto;
import com.example.todolist.application.inbound.category.RenameCategory;
import com.example.todolist.application.outbound.category.ActiveCategoryExtractor;
import com.example.todolist.application.outbound.category.CategoryPersister;
import com.example.todolist.domain.exception.CategoryAccessDeniedException;
import com.example.todolist.domain.exception.CategoryNotFoundException;
import com.example.todolist.domain.model.Title;
import com.example.todolist.domain.model.category.Category;
import com.example.todolist.domain.model.category.CategoryId;
import com.example.todolist.domain.model.category.events.CategoryTitleRenamedEvent;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;


@Component
class RenameCategoryUseCase implements RenameCategory {

    //TODO: check if we need use other extractor e.g rename not active or deleted category
    private final ActiveCategoryExtractor categoryExtractor;
    private final CategoryPersister categoryPersister;
    private final ApplicationEventPublisher applicationEventPublisher;


    RenameCategoryUseCase(
            ActiveCategoryExtractor categoryExtractor,
            CategoryPersister categoryPersister,
            ApplicationEventPublisher applicationEventPublisher) {
        this.categoryExtractor = categoryExtractor;
        this.categoryPersister = categoryPersister;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    @Transactional
    public void execute(RenameCategoryDto dto) {
        final var categoryId = CategoryId.of(dto.categoryId());
        final var title = Title.of(dto.title());

        final var category = categoryExtractor.getActiveCategoryById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId.getValue()));

        if (!category.belongsToUser(dto.userId())) {
            throw new CategoryAccessDeniedException(dto.categoryId(), dto.userId());
        }

        Category updated = category.updateTitle(title);
        categoryPersister.persist(updated);

        var event = new CategoryTitleRenamedEvent(
                updated.getId().getValue(),
                updated.getTitle().getValue()
        );
        applicationEventPublisher.publishEvent(event);
    }
}
