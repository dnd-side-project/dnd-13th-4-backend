package com.example.wini.domain.template.repository;

import static com.example.wini.domain.template.domain.QActionCategory.actionCategory;

import com.example.wini.domain.template.domain.ActionCategory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ActionCategoryCustomRepositoryImpl implements ActionCategoryCustomRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<ActionCategory> findAllWithActions() {
    return queryFactory.selectFrom(actionCategory).join(actionCategory.actions).fetch();
  }
}
