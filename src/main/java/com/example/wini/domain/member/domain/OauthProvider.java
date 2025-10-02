package com.example.wini.domain.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OauthProvider {
    KAKAO("kakao"),
    APPLE("apple"),
    NONE(""),
    ;

    private final String value;
}
