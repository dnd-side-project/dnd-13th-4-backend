package com.example.wini.domain.template.repository;

import static com.example.wini.domain.template.domain.QClosing.closing;

import com.example.wini.domain.template.domain.Closing;
import com.example.wini.domain.template.domain.EmotionType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClosingCustomRepositoryImpl implements ClosingCustomRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<Closing> findAllByEmotionType(EmotionType emotionType) {
    return queryFactory.selectFrom(closing).where(closing.emotionType.eq(emotionType)).fetch();
  }
}
