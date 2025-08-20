package com.example.wini.domain.template.dto.response;

import com.example.wini.domain.template.domain.Action;

public record ActionResponse(Long id, String text) {
  public static ActionResponse from(Action action) {
    return new ActionResponse(action.getId(), action.getText());
  }
}
