package com.example.wini.domain.log.dto.response;

import com.example.wini.domain.template.domain.ActionCategory;
import com.example.wini.domain.template.dto.response.ActionCategoryResponse;

public record KeywordResponse(
        ActionCategoryResponse positiveActionCategory, ActionCategoryResponse negativeActionCategory) {
    public static KeywordResponse from(ActionCategory positive, ActionCategory negative) {
        return new KeywordResponse(ActionCategoryResponse.from(positive), ActionCategoryResponse.from(negative));
    }
}
