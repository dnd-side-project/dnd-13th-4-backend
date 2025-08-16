package com.example.wini.domain.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Location {
  HOME("집"),
  OUTDOORS("야외"),
  ;

  private final String value;
}
