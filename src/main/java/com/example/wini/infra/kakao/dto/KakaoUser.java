package com.example.wini.infra.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoUser(Long id, @JsonProperty("kakao_account") KakaoAccount kakaoAccount) {}
