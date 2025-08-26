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
public class Situation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private EmotionType emotionType;

    @Column(length = 50, nullable = false)
    private String text;

    @Builder(access = AccessLevel.PRIVATE)
    private Situation(EmotionType emotionType, String text) {
        this.emotionType = emotionType;
        this.text = text;
    }

    public static Situation create(EmotionType emotionType, String text) {
        return Situation.builder().emotionType(emotionType).text(text).build();
    }
}
