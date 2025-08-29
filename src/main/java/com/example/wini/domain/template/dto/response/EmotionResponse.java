package com.example.wini.domain.template.dto.response;

import com.example.wini.domain.template.domain.Emotion;

public record EmotionResponse(
        Long id,
        String emotionType,
        String text,
        String selectionImageUrl,
        String previewImageUrl,
        String archiveImageUrl,
        String homeThumbnailUrl) {
    public static EmotionResponse from(Emotion emotion) {
        return new EmotionResponse(
                emotion.getId(),
                emotion.getEmotionType().getValue(),
                emotion.getText(),
                emotion.getSelectionImageUrl(),
                emotion.getPreviewImageUrl(),
                emotion.getArchiveImageUrl(),
                emotion.getHomeThumbnailUrl());
    }
}
