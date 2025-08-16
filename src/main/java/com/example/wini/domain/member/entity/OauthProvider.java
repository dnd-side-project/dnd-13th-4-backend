package com.example.wini.domain.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OauthProvider {
  KAKAO("kakao");

  private final String value;
}
