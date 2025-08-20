package com.example.wini.domain.template.service;

import com.example.wini.domain.template.domain.ActionCategory;
import com.example.wini.domain.template.domain.EmotionType;
import com.example.wini.domain.template.dto.response.ActionCategoryResponse;
import com.example.wini.domain.template.repository.action.ActionCategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActionService {

  private final ActionCategoryRepository actionCategoryRepository;

  @Transactional(readOnly = true)
  public List<ActionCategoryResponse> findAllActionCategoriesByEmotionType(String emotionType) {
    EmotionType type = EmotionType.from(emotionType);
    List<ActionCategory> categories =
        actionCategoryRepository.findAllWithActionsByEmotionType(type);
    return categories.stream().map(ActionCategoryResponse::from).toList();
  }
}
