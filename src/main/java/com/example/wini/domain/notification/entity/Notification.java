package com.example.wini.domain.notification.entity;

import com.example.wini.domain.common.BaseEntity;
import com.example.wini.domain.member.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", unique = true)
    private Member member;

    @Column(length = 500, nullable = false, unique = true)
    private String token;

    @Builder(access = AccessLevel.PRIVATE)
    private Notification(Member member, String token) {
        this.member = member;
        this.token = token;
    }

    public static Notification create(Member member, String token) {
        return Notification.builder().member(member).token(token).build();
    }
}
