package com.example.wini.domain.template.repository.promise;

import com.example.wini.domain.template.domain.EmotionType;
import com.example.wini.domain.template.domain.Promise;
import java.util.List;

public interface PromiseCustomRepository {
    List<Promise> findAllByEmotionType(EmotionType emotionType);
}
