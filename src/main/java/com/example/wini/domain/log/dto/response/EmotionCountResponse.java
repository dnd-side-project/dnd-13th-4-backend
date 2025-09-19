package com.example.wini.domain.log.dto.response;

public record EmotionCountResponse(Long id, Long count) {
    public static EmotionCountResponse from(EmotionCount emotionCount) {
        if (emotionCount == null) {
            return new EmotionCountResponse(null, null);
        }
        return new EmotionCountResponse(emotionCount.emotion().getId(), emotionCount.count());
    }
}
