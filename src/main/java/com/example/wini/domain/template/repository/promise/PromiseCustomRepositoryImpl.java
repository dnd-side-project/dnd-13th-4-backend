package com.example.wini.domain.template.repository.promise;

import static com.example.wini.domain.template.domain.QPromise.promise;

import com.example.wini.domain.template.domain.EmotionType;
import com.example.wini.domain.template.domain.Promise;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PromiseCustomRepositoryImpl implements PromiseCustomRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<Promise> findAllByEmotionType(EmotionType emotionType) {
    return queryFactory.selectFrom(promise).where(promise.emotionType.eq(emotionType)).fetch();
  }
}
