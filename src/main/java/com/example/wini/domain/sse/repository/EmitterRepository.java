package com.example.wini.domain.sse.repository;

import java.util.Map;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface EmitterRepository {

    SseEmitter save(String emitterId, SseEmitter sseEmitter);

    void saveEventCache(String eventCacheId, Object event);

    void deleteById(String emitterId);

    Map<String, SseEmitter> findAllStartWithMemberId(String memberId);

    Map<String, Object> findAllEventCacheStartWithMemberId(String memberId);
}
