package com.example.wini.domain.member.domain;

import com.example.wini.domain.auth.dto.common.OauthMemberInfo;
import com.example.wini.domain.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"oauthId", "oauthProvider"})})
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private Status status;

    @Column
    private String email;

    @Column(length = 10, nullable = false)
    private String name;

    private String image;

    @Column(nullable = false)
    private String oauthId;

    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private OauthProvider oauthProvider;

    private LocalDateTime statusStartedAt;

    private Long statusDuration;

    @Builder(access = AccessLevel.PRIVATE)
    private Member(String name, String image, String oauthId, OauthProvider oauthProvider) {
        this.name = name;
        this.image = image;
        this.oauthId = oauthId;
        this.oauthProvider = oauthProvider;
    }

    public static Member create(OauthMemberInfo oauthMemberInfo, OauthProvider oauthProvider) {
        return Member.builder()
                .name(oauthMemberInfo.name())
                .image(oauthMemberInfo.imageUrl())
                .oauthId(oauthMemberInfo.providerId())
                .oauthProvider(oauthProvider)
                .build();
    }

    public void updateStatus(Status status, LocalDateTime statusStartedAt, Long statusDuration) {
        this.status = status;
        this.statusStartedAt = statusStartedAt;
        this.statusDuration = statusDuration;
    }
}
