package com.example.todolist.infrastructure.persistence.converter;

import com.example.todolist.domain.model.TodoStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class TodoStatusConverter implements AttributeConverter<TodoStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TodoStatus status) {
        return status.getId();
    }

    @Override
    public TodoStatus convertToEntityAttribute(Integer dbData) {
        return TodoStatus.byId(dbData);
    }
}
