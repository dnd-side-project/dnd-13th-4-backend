package com.example.wini.domain.template.domain;

import com.example.wini.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ActionCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private EmotionType emotionType;

    @Column(length = 10, nullable = false)
    private String name;

    @Builder(access = AccessLevel.PRIVATE)
    private ActionCategory(EmotionType emotionType, String name) {
        this.emotionType = emotionType;
        this.name = name;
    }

    public static ActionCategory create(EmotionType emotionType, String name) {
        return ActionCategory.builder().emotionType(emotionType).name(name).build();
    }
}
