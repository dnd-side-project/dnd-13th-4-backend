package com.example.wini.domain.template.repository;

import static com.example.wini.domain.template.domain.QAction.action;

import com.example.wini.domain.template.domain.Action;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ActionCustomRepositoryImpl implements ActionCustomRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<Action> findAllWithCategory() {
    return queryFactory.selectFrom(action).join(action.category).fetch();
  }
}
