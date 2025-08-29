package com.example.wini.infra.fcm.dto;

import com.example.wini.domain.notification.domain.NotificationType;
import java.util.Optional;

public record FcmMessageRequest(String token, String title, String body, String type, Optional<Long> entityId) {

    public static FcmMessageRequest from(
            String token, NotificationType notificationType, Optional<String> arg, Optional<Long> entityId) {

        String body = arg.map(notificationType::formatBody).orElse(notificationType.getBody());

        return new FcmMessageRequest(token, notificationType.getTitle(), body, notificationType.getType(), entityId);
    }
}
