package com.example.wini.domain.auth.dto.common;

import com.example.wini.infra.kakao.dto.KakaoUser;

public record OauthMemberInfo(String providerId, String name, String imageUrl) {

    public static OauthMemberInfo from(KakaoUser kakaoUser) {
        return new OauthMemberInfo(
                String.valueOf(kakaoUser.id()),
                kakaoUser.kakaoAccount().profile().nickname(),
                kakaoUser.kakaoAccount().profile().profileImageUrl());
    }
}
