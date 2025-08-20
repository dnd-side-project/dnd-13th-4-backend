package com.example.wini.domain.template.repository.action;

import static com.example.wini.domain.template.domain.QActionCategory.actionCategory;

import com.example.wini.domain.template.domain.ActionCategory;
import com.example.wini.domain.template.domain.EmotionType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ActionCategoryCustomRepositoryImpl implements ActionCategoryCustomRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<ActionCategory> findAllWithActionsByEmotionType(EmotionType emotionType) {
    return queryFactory
        .selectFrom(actionCategory)
        .join(actionCategory.actions)
        .where(actionCategory.emotionType.eq(emotionType))
        .fetch();
  }
}
