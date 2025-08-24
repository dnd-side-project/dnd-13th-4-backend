package com.example.wini.infra.fcm.converter;

import com.example.wini.infra.fcm.dto.FcmMessageRequest;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidConfig.Priority;
import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Component;

@Component
public class FcmMessageConverter {

    public Message convert(FcmMessageRequest request) {
        return Message.builder()
                .setNotification(createNotification(request))
                .setToken(request.token())
                .setApnsConfig(createApnsConfig())
                .setAndroidConfig(createAndroidConfig())
                .putData("type", request.type())
                .build();
    }

    private Notification createNotification(FcmMessageRequest request) {
        return Notification.builder()
                .setTitle(request.title())
                .setBody(request.body())
                .build();
    }

    private ApnsConfig createApnsConfig() {
        return ApnsConfig.builder()
                .setAps(Aps.builder().setContentAvailable(true).build())
                .build();
    }

    private AndroidConfig createAndroidConfig() {
        return AndroidConfig.builder().setPriority(Priority.NORMAL).build();
    }
}
