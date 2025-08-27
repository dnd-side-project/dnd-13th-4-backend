package com.example.wini.domain.notification.service;

import com.example.wini.domain.common.util.MemberUtil;
import com.example.wini.domain.member.domain.Member;
import com.example.wini.domain.notification.domain.FirebaseToken;
import com.example.wini.domain.notification.dto.request.FirebaseTokenSaveRequest;
import com.example.wini.domain.notification.repository.FirebaseTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FirebaseTokenService {

    private final FirebaseTokenRepository firebaseTokenRepository;
    private final MemberUtil memberUtil;

    @Transactional
    public void saveFirebaseToken(FirebaseTokenSaveRequest request) {
        Member member = memberUtil.getCurrentMember();
        String token = request.token();

        firebaseTokenRepository.deleteByToken(token);

        FirebaseToken firebaseToken = FirebaseToken.create(member, token);
        firebaseTokenRepository.save(firebaseToken);
    }
}
