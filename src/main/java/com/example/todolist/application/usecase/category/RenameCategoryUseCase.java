package com.example.todolist.application.usecase.category;

import com.example.todolist.application.dto.category.RenameCategoryDto;
import com.example.todolist.application.inbound.category.RenameCategory;
import com.example.todolist.application.outbound.category.ActiveCategoryExtractor;
import com.example.todolist.application.outbound.category.CategoryPersister;
import com.example.todolist.domain.model.Title;
import com.example.todolist.domain.model.category.Category;
import com.example.todolist.domain.model.category.CategoryId;
import com.example.todolist.domain.model.category.events.CategoryTitleRenamedEvent;
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
    public void execute(RenameCategoryDto dto) {
        final var id = CategoryId.of(dto.id());
        final var title = Title.of(dto.title());

        final var category = categoryExtractor.getActiveCategoryById(id);

        category.ifPresentOrElse(c ->
        {
            Category updated = c.updateTitle(title);
            categoryPersister.persist(updated);

            var event = new CategoryTitleRenamedEvent(
                    updated.getId().getValue(),
                    updated.getTitle().getValue()
            );
            applicationEventPublisher.publishEvent(event);
        }, () -> System.out.println("Error"));

    }
}
