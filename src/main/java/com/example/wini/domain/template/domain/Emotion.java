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
public class Emotion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private EmotionType emotionType;

    @Column(length = 10, nullable = false)
    private String text;

    @Column(nullable = false)
    private String selectionImageUrl;

    @Column(nullable = false)
    private String previewImageUrl;

    @Column(nullable = false)
    private String archiveImageUrl;

    @Column(nullable = false)
    private String homeThumbnailUrl;

    @Builder(access = AccessLevel.PRIVATE)
    private Emotion(
            EmotionType emotionType,
            String text,
            String selectionImageUrl,
            String previewImageUrl,
            String archiveImageUrl,
            String homeThumbnailUrl) {
        this.emotionType = emotionType;
        this.text = text;
        this.selectionImageUrl = selectionImageUrl;
        this.previewImageUrl = previewImageUrl;
        this.archiveImageUrl = archiveImageUrl;
        this.homeThumbnailUrl = homeThumbnailUrl;
    }

    public static Emotion create(
            EmotionType emotionType,
            String text,
            String selectionImageUrl,
            String previewImageUrl,
            String archiveImageUrl,
            String homeThumbnailUrl) {
        return Emotion.builder()
                .emotionType(emotionType)
                .text(text)
                .selectionImageUrl(selectionImageUrl)
                .previewImageUrl(previewImageUrl)
                .archiveImageUrl(archiveImageUrl)
                .homeThumbnailUrl(homeThumbnailUrl)
                .build();
    }
}
