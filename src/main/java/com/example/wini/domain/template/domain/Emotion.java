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
  private String graphicUrl;

  @Builder(access = AccessLevel.PRIVATE)
  private Emotion(EmotionType emotionType, String text, String graphicUrl) {
    this.emotionType = emotionType;
    this.text = text;
    this.graphicUrl = graphicUrl;
  }

  public static Emotion create(EmotionType emotionType, String text, String graphicUrl) {
    return Emotion.builder().emotionType(emotionType).text(text).graphicUrl(graphicUrl).build();
  }
}
