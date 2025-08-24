package com.example.wini.global.config;

import static com.example.wini.global.error.exception.ErrorCode.FIREBASE_INITIALIZATION_FAILED;
import static com.example.wini.global.error.exception.ErrorCode.FIREBASE_KEY_FILE_NOT_FOUND;

import com.example.wini.global.error.exception.CustomException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import jakarta.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirebaseConfig {

    @Value("${fcm.secret-key}")
    private String fcmSecretKey;

    @PostConstruct
    public void init() {
        try {
            InputStream keyFileStream = new ByteArrayInputStream(fcmSecretKey.getBytes());
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(keyFileStream))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
        } catch (IOException e) {
            throw new CustomException(FIREBASE_KEY_FILE_NOT_FOUND);
        } catch (Exception e) {
            throw new CustomException(FIREBASE_INITIALIZATION_FAILED);
        }
    }

    @Bean
    FirebaseApp firebaseApp() {
        return FirebaseApp.getInstance();
    }

    @Bean
    FirebaseMessaging firebaseMessaging() {
        return FirebaseMessaging.getInstance(firebaseApp());
    }
}
