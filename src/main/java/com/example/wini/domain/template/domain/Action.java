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
public class Action extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "action_category_id")
    private ActionCategory actionCategory;

    @Column(length = 50, nullable = false)
    private String text;

    @Builder(access = AccessLevel.PRIVATE)
    private Action(ActionCategory actionCategory, String text) {
        this.actionCategory = actionCategory;
        this.text = text;
    }

    public static Action create(ActionCategory actionCategory, String text) {
        return Action.builder().actionCategory(actionCategory).text(text).build();
    }
}
