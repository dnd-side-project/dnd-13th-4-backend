package com.example.wini.domain.log.dto.response;

import com.example.wini.domain.template.domain.Action;

public record ActionChange(Action action, long monthlyChange) {}
