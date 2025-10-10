package com.example.todolist.application.usecase;

import com.example.todolist.application.dto.TodoResponse;
import com.example.todolist.application.dto.TodoStatusResponse;
import com.example.todolist.domain.model.TodoStatus;
import com.example.todolist.domain.repository.TodoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class GetNonCategoryTodosUseCase implements GetNonCategoryTodos {
    private final TodoRepository todoRepository;

    GetNonCategoryTodosUseCase(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public List<TodoResponse> execute(Long userId, String statusUrlParam) {
        var status = TodoStatus.fromUrlParam(statusUrlParam);

        var todos = todoRepository.findByUserIdAndCategoryIdAndDeletedAtIsNullAndStatusOrderByIdDesc(userId, null, status);

        return todos
                .stream()
                .map(item -> new TodoResponse(
                        item.getId(),
                        item.getTitle(),
                        item.getDescription(),
                        item.getCategoryId(),
                        new TodoStatusResponse(
                                item.getStatus().getId(),
                                item.getStatus().getTitle()
                        ),
                        item.getCreatedAt(),
                        item.getPlannedAt()
                ))
                .toList();
    }
}
