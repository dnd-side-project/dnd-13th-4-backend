package com.example.wini.domain.member.entity;

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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import lombok.AccessLevel;
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

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "status_id")
  private Status status;

  @Column(nullable = false)
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

  private LocalDateTime statusDuration;
}
