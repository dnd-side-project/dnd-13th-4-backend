package com.example.wini.domain.member.service;

import static com.example.wini.global.error.exception.ErrorCode.MATE_NOT_FOUND;
import static com.example.wini.global.error.exception.ErrorCode.STATUS_NOT_FOUND;

import com.example.wini.domain.common.util.MemberUtil;
import com.example.wini.domain.member.domain.Member;
import com.example.wini.domain.member.domain.Status;
import com.example.wini.domain.member.dto.common.ReservedTimeInfo;
import com.example.wini.domain.member.dto.request.MemberStatusUpdateRequest;
import com.example.wini.domain.member.dto.response.MemberResponse;
import com.example.wini.domain.member.dto.response.MemberStatusResponse;
import com.example.wini.domain.member.repository.MemberRepository;
import com.example.wini.domain.member.repository.StatusRepository;
import com.example.wini.domain.notification.domain.NotificationType;
import com.example.wini.domain.notification.event.NotificationEvent;
import com.example.wini.domain.room.repository.RoomRepository;
import com.example.wini.global.error.exception.CustomException;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;
    private final StatusRepository statusRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final MemberUtil memberUtil;

    @Transactional(readOnly = true)
    public MemberStatusResponse searchMyStatus() {
        Member member = memberUtil.getCurrentMember();
        if (!isStatusValid(member)) {
            return MemberStatusResponse.empty();
        }

        ReservedTimeInfo reservedTimeInfo = createReservedTimeInfo(member.getStatusDuration());

        return MemberStatusResponse.from(member, reservedTimeInfo);
    }

    @Transactional(readOnly = true)
    public MemberStatusResponse searchMateStatus() {
        Long myMemberId = memberUtil.getCurrentMemberId();
        Member mate = memberRepository
                .findRoommateWithStatusByMemberId(myMemberId)
                .orElseThrow(() -> new CustomException(MATE_NOT_FOUND));

        if (!isStatusValid(mate)) {
            return MemberStatusResponse.empty();
        }

        ReservedTimeInfo reservedTimeInfo = createReservedTimeInfo(mate.getStatusDuration());

        return MemberStatusResponse.from(mate, reservedTimeInfo);
    }

    private boolean isStatusValid(Member member) {
        if (member.getStatus() == null) {
            return false;
        }
        LocalDateTime endTime = member.getStatusStartedAt().plus(Duration.ofSeconds(member.getStatusDuration()));
        return endTime.isAfter(LocalDateTime.now());
    }

    private ReservedTimeInfo createReservedTimeInfo(long durationSeconds) {
        Duration duration = Duration.ofSeconds(durationSeconds);
        return ReservedTimeInfo.of(duration.toHours(), duration.toMinutes() % 60);
    }

    @Transactional
    public MemberStatusResponse updateStatus(MemberStatusUpdateRequest request) {
        Member member = memberUtil.getCurrentMember();

        Status status =
                statusRepository.findById(request.statusId()).orElseThrow(() -> new CustomException(STATUS_NOT_FOUND));

        Duration statusDuration = request.reservedTimeInfo().toDuration();
        Long statusDurationSeconds = statusDuration.getSeconds();

        member.updateStatus(status, request.startedAt(), statusDurationSeconds);
        notifyRoommateOfStatusUpdate(member.getId());

        return MemberStatusResponse.from(member, request.reservedTimeInfo());
    }

    private void notifyRoommateOfStatusUpdate(Long memberId) {
        Member mate = memberRepository
                .findRoommateByMemberId(memberId)
                .orElseThrow(() -> new CustomException(MATE_NOT_FOUND));
        NotificationEvent event = NotificationEvent.from(mate.getId(), NotificationType.NEW_STATUS);
        eventPublisher.publishEvent(event);
    }

    @Transactional(readOnly = true)
    public MemberResponse getMyInfo() {
        Member member = memberUtil.getCurrentMember();
        return MemberResponse.from(member);
    }
}
