package com.example.wini.domain.member.dto.common;

public record ReservedTimeInfo(Long hour, Long minute) {
  public static ReservedTimeInfo of(long hour, long minute) {
    return new ReservedTimeInfo(hour, minute);
  }
}
