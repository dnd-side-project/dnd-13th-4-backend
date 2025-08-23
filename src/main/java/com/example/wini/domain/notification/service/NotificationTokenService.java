package com.example.wini.domain.notification.service;

import static com.example.wini.global.error.exception.ErrorCode.MEMBER_NOT_FOUND;

import com.example.wini.domain.member.domain.Member;
import com.example.wini.domain.member.repository.MemberRepository;
import com.example.wini.domain.notification.domain.NotificationToken;
import com.example.wini.domain.notification.dto.request.NotificationTokenSaveRequest;
import com.example.wini.domain.notification.repository.NotificationTokenRepository;
import com.example.wini.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationTokenService {

    private final NotificationTokenRepository notificationTokenRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void saveNotificationToken(Long memberId, NotificationTokenSaveRequest request) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
        String token = request.token();

        notificationTokenRepository.deleteByToken(token);

        NotificationToken notificationToken = NotificationToken.create(member, token);
        notificationTokenRepository.save(notificationToken);
    }
}
