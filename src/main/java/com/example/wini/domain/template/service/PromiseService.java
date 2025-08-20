package com.example.wini.domain.template.service;

import com.example.wini.domain.template.domain.EmotionType;
import com.example.wini.domain.template.domain.Promise;
import com.example.wini.domain.template.dto.response.PromiseResponse;
import com.example.wini.domain.template.repository.promise.PromiseRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PromiseService {

    private final PromiseRepository promiseRepository;

    @Transactional(readOnly = true)
    public List<PromiseResponse> findAllPromisesByEmotionType(String emotionType) {
        EmotionType type = EmotionType.from(emotionType);
        List<Promise> promises = promiseRepository.findAllByEmotionType(type);
        return promises.stream().map(PromiseResponse::from).toList();
    }
}
