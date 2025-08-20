package com.example.wini.domain.template.domain;

import com.example.wini.domain.common.BaseEntity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
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

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "action_category_id")
  private List<Action> actions = new ArrayList<>();

  @Builder(access = AccessLevel.PRIVATE)
  private ActionCategory(EmotionType emotionType, String name, List<Action> actions) {
    this.emotionType = emotionType;
    this.name = name;
    this.actions = actions;
  }

  public static ActionCategory create(EmotionType emotionType, String name, List<Action> actions) {
    return ActionCategory.builder().emotionType(emotionType).name(name).actions(actions).build();
  }
}
