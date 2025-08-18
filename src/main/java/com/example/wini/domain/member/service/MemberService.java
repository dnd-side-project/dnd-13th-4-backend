package com.example.wini.domain.member.service;

import static com.example.wini.global.error.exception.ErrorCode.MEMBER_NOT_FOUND;

import com.example.wini.domain.member.domain.Member;
import com.example.wini.domain.member.dto.common.ReservedTimeInfo;
import com.example.wini.domain.member.dto.response.MemberStatusResponse;
import com.example.wini.domain.member.repository.MemberRepository;
import com.example.wini.global.error.exception.CustomException;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

  private static final Long MEMBER_ID = 1L;

  public MemberStatusResponse searchMyStatus() {
    Member member =
        memberRepository
            .findWithStatusByMemberId(MEMBER_ID)
            .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

    if (!isStatusValid(member)) {
      return MemberStatusResponse.empty();
    }

    ReservedTimeInfo reservedTimeInfo = createReservedTimeInfo(member.getStatusDuration());

    return MemberStatusResponse.from(member, reservedTimeInfo);
  }

  private boolean isStatusValid(Member member) {
    if (member.getStatus() == null) {
      return false;
    }
    LocalDateTime endTime =
        member.getStatusStartedAt().plus(Duration.ofSeconds(member.getStatusDuration()));
    return endTime.isAfter(LocalDateTime.now());
  }

  private ReservedTimeInfo createReservedTimeInfo(long durationSeconds) {
    Duration duration = Duration.ofSeconds(durationSeconds);
    return ReservedTimeInfo.of(duration.toHours(), duration.toMinutes() % 60);
  }
}
