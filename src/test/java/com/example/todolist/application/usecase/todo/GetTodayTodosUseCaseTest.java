package com.example.todolist.application.usecase.todo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GetTodayTodosUseCaseTest {

    @Test
    void execute() {
        var useCase = new GetTodayTodosUseCase(
                new TodayActiveTodosExtractorMock()
        );

        var result = useCase.execute(20L);

        Assertions.assertEquals(1, result.size());
    }
}