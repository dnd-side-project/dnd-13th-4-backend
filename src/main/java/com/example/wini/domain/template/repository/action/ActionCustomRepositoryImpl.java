package com.example.wini.domain.template.repository.action;

import static com.example.wini.domain.template.domain.QAction.action;
import static com.example.wini.domain.template.domain.QActionCategory.actionCategory;

import com.example.wini.domain.template.domain.Action;
import com.example.wini.domain.template.domain.ActionCategory;
import com.example.wini.domain.template.domain.EmotionType;
import com.querydsl.core.group.GroupBy;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ActionCustomRepositoryImpl implements ActionCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Map<ActionCategory, List<Action>> findActionsGroupedByCategoryByEmotionType(EmotionType emotionType) {
        return queryFactory
                .select(actionCategory, action)
                .from(action)
                .join(action.actionCategory, actionCategory)
                .where(actionCategory.emotionType.eq(emotionType))
                .transform(GroupBy.groupBy(actionCategory).as(GroupBy.list(action)));
    }
}
