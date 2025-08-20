package com.example.wini.domain.template.dto.response;

import com.example.wini.domain.template.domain.ActionCategory;
import java.util.List;

public record ActionCategoryResponse(
    Long id, String emotionType, String name, List<ActionResponse> actions) {
  public static ActionCategoryResponse from(ActionCategory category) {
    List<ActionResponse> actions =
        category.getActions().stream().map(ActionResponse::from).toList();
    return new ActionCategoryResponse(
        category.getId(), category.getEmotionType().getValue(), category.getName(), actions);
  }
}
