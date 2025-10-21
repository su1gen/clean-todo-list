package com.example.todolist.application.inbound.category;

import com.example.todolist.application.dto.category.RenameCategoryDto;

public interface RenameCategory {
    void execute(RenameCategoryDto dto);
}
