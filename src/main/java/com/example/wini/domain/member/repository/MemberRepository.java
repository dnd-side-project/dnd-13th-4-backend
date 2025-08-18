package com.example.wini.domain.member.repository;

import com.example.wini.domain.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

  @EntityGraph(attributePaths = "status")
  Optional<Member> findWithStatusByMemberId(Long memberId);
}
