package com.example.wini.domain.template.repository.emotion;

import static com.example.wini.domain.template.domain.QEmotion.emotion;

import com.example.wini.domain.template.domain.Emotion;
import com.example.wini.domain.template.domain.EmotionType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmotionCustomRepositoryImpl implements EmotionCustomRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<Emotion> findAllByEmotionType(EmotionType emotionType) {
    return queryFactory.selectFrom(emotion).where(emotion.emotionType.eq(emotionType)).fetch();
  }
}
