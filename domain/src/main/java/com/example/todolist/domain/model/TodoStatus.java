package com.example.todolist.domain.model;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Value Object для статуса задачи.
 *
 * Это доменная модель, независимая от БД.
 * Инкапсулирует бизнес-правила переходов между статусами. возможно в будущем
 */
public enum TodoStatus {
    CREATED(3, "Created", "created"),
    CONFIRMED(4, "Confirmed", "confirmed"),
    IN_PROCESS(0, "In process", "in-process"),
    FINISHED(1, "Finished", "finished"),
    NOT_RELEVANT(2, "Not relevant", "not-relevant");

    // Индексы для быстрого поиска
    private static final Map<Integer, TodoStatus> BY_ID = Arrays.stream(TodoStatus.values())
            .collect(Collectors.toMap(TodoStatus::getId, Function.identity()));

    public static final Map<String, TodoStatus> BY_TITLE = Arrays.stream(TodoStatus.values())
            .collect(Collectors.toMap(TodoStatus::getTitle, Function.identity()));

    private static final Map<String, TodoStatus> BY_URL_PARAM = Arrays.stream(TodoStatus.values())
            .collect(Collectors.toMap(TodoStatus::getUrlParam, Function.identity()));

    private final int id;
    private final String title;
    private final String urlParam;

    TodoStatus(int id, String title, String urlParam) {
        this.id = id;
        this.title = title;
        this.urlParam = urlParam;
    }

    // Фабричные методы
    public static TodoStatus fromId(int id) {
        TodoStatus status = BY_ID.get(id);
        if (status == null) {
            throw new IllegalArgumentException("Invalid TodoStatus id: " + id);
        }
        return status;
    }

    public static TodoStatus fromTitle(String title) {
        TodoStatus status = BY_TITLE.get(title);
        if (status == null) {
            throw new IllegalArgumentException("Invalid TodoStatus title: " + title);
        }
        return status;
    }

    public static TodoStatus fromUrlParam(String urlParam) {
        TodoStatus status = BY_URL_PARAM.get(urlParam);
        if (status == null) {
            throw new IllegalArgumentException("Invalid TodoStatus urlParam: " + urlParam);
        }
        return status;
    }

    // Бизнес-правила переходов между статусами
    public boolean canTransitionTo(TodoStatus newStatus) {
        if (this == newStatus) {
            return true; // Можно "перейти" в тот же статус
        }

        return switch (this) {
            case CREATED -> newStatus == CONFIRMED || newStatus == NOT_RELEVANT;
            case CONFIRMED -> newStatus == IN_PROCESS || newStatus == NOT_RELEVANT;
            case IN_PROCESS -> newStatus == FINISHED || newStatus == NOT_RELEVANT;
            case FINISHED -> newStatus == IN_PROCESS; // Можно вернуть в работу
            case NOT_RELEVANT -> newStatus == CREATED; // Можно восстановить
        };
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getUrlParam() { return urlParam; }

    @Override
    public String toString() {
        return title;
    }
}
