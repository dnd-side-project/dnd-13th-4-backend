package com.example.wini.domain.template.dto.response;

import com.example.wini.domain.template.domain.ActionCategory;

public record ActionCategoryResponse(Long id, String emotionType, String name) {
    public static ActionCategoryResponse from(ActionCategory category) {
        return new ActionCategoryResponse(
                category.getId(), category.getEmotionType().getValue(), category.getName());
    }
}
