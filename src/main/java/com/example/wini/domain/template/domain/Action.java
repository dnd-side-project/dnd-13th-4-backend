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

    @Column(length = 30, nullable = false)
    private String text;

    @Builder(access = AccessLevel.PRIVATE)
    private Action(String text) {
        this.text = text;
    }

    public static Action create(String text) {
        return Action.builder().text(text).build();
    }
}
