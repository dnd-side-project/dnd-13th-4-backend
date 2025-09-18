package com.example.wini.domain.sse.service;

import com.example.wini.domain.common.util.MemberUtil;
import com.example.wini.domain.member.domain.Member;
import com.example.wini.domain.sse.repository.EmitterRepository;
import com.example.wini.global.error.exception.CustomException;
import com.example.wini.global.error.exception.ErrorCode;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@Service
@RequiredArgsConstructor
public class SseService {

    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    private final EmitterRepository emitterRepository;
    private final MemberUtil memberUtil;

    public SseEmitter subscribe(String lastEventId) {
        Long memberId = memberUtil.getCurrentMemberId();
        String id = memberId + "_" + System.currentTimeMillis();

        SseEmitter emitter = emitterRepository.save(id, new SseEmitter(DEFAULT_TIMEOUT));

        emitter.onCompletion(() -> emitterRepository.deleteById(id));
        emitter.onTimeout(() -> emitterRepository.deleteById(id));

        sendToClient(emitter, id, "EventStream Created. [memberId=" + memberId + "]");

        if (!lastEventId.isEmpty()) {
            Map<String, Object> events = emitterRepository.findAllEventCacheStartWithMemberId(String.valueOf(memberId));
            events.entrySet().stream()
                    .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                    .forEach(entry -> sendToClient(emitter, entry.getKey(), entry.getValue()));
        }

        return emitter;
    }

    public void send(Member receiver, Object data) {
        String receiverId = String.valueOf(receiver.getId());

        Map<String, SseEmitter> sseEmitters = emitterRepository.findAllStartWithMemberId(receiverId);
        sseEmitters.forEach((key, emitter) -> {
            emitterRepository.saveEventCache(key, data);
            sendToClient(emitter, key, data);
        });
    }

    private void sendToClient(SseEmitter emitter, String id, Object data) {
        try {
            emitter.send(SseEmitter.event().id(id).name("sse").data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(id);
            throw new CustomException(ErrorCode.SSE_CONNECTION_ERROR);
        }
    }
}
