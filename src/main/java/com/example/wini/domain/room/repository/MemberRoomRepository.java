package com.example.wini.domain.room.repository;

import com.example.wini.domain.room.entity.MemberRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRoomRepository extends JpaRepository<MemberRoom, Long> {

  Long countMembersByRoomId(Long roomId);
}
