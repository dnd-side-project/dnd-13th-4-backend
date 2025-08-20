package com.example.wini.domain.template.repository.situation;

import static com.example.wini.domain.template.domain.QSituation.situation;

import com.example.wini.domain.template.domain.EmotionType;
import com.example.wini.domain.template.domain.Situation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SituationCustomRepositoryImpl implements SituationCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Situation> findAllByEmotionType(EmotionType emotionType) {
        return queryFactory
                .selectFrom(situation)
                .where(situation.emotionType.eq(emotionType))
                .fetch();
    }
}
