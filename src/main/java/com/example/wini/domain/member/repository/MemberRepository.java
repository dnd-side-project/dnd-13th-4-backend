package com.example.wini.domain.member.repository;

import com.example.wini.domain.member.domain.Member;
import com.example.wini.domain.member.domain.OauthProvider;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository {

    Optional<Member> findByOauthIdAndOauthProvider(String oauthId, OauthProvider oauthProvider);
}
