package com.example.wini.domain.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
    uniqueConstraints = {@UniqueConstraint(columnNames = {"emoji", "text", "request", "location"})})
public class Status {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 1, nullable = false)
  private String emoji;

  @Column(length = 10, nullable = false)
  private String text;

  @Column(length = 20, nullable = false)
  private String request;

  @Column(length = 10, nullable = false)
  @Enumerated(EnumType.STRING)
  private Location location;
}
