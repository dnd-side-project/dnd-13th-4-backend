package com.example.wini.domain.notification.service;

import static com.example.wini.global.error.exception.ErrorCode.MEMBER_NOT_FOUND;

import com.example.wini.domain.member.domain.Member;
import com.example.wini.domain.member.repository.MemberRepository;
import com.example.wini.domain.notification.dto.request.NotificationCreateRequest;
import com.example.wini.domain.notification.entity.Notification;
import com.example.wini.domain.notification.repository.NotificationRepository;
import com.example.wini.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void saveNotificationToken(Long memberId, NotificationCreateRequest request) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
        String token = request.token();

        notificationRepository.deleteByMember(member);
        notificationRepository.deleteByToken(token);

        Notification notification = Notification.create(member, token);
        notificationRepository.save(notification);
    }
}
