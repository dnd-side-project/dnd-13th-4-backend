package com.example.wini.domain.notification.service;

import static com.example.wini.global.error.exception.ErrorCode.MEMBER_NOT_FOUND;

import com.example.wini.domain.member.domain.Member;
import com.example.wini.domain.member.repository.MemberRepository;
import com.example.wini.domain.notification.domain.FirebaseToken;
import com.example.wini.domain.notification.dto.request.FirebaseTokenSaveRequest;
import com.example.wini.domain.notification.repository.FirebaseTokenRepository;
import com.example.wini.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FirebaseTokenService {

    private final FirebaseTokenRepository firebaseTokenRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void saveFirebaseToken(Long memberId, FirebaseTokenSaveRequest request) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
        String token = request.token();

        firebaseTokenRepository.deleteByToken(token);

        FirebaseToken firebaseToken = FirebaseToken.create(member, token);
        firebaseTokenRepository.save(firebaseToken);
    }
}
