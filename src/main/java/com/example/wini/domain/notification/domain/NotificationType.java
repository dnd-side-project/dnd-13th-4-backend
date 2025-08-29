package com.example.wini.domain.notification.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {
    NEW_ROOMMATE("join", "Wini", "룸메이트가 나의 초대에 응했어요.\n이제 함께 Wini를 시작해보세요."),
    NEW_NOTE("note", "Wini", "24시간 내 사라지는 룸메이트의 마음쪽지가 도착했어요."),
    NEW_STATUS("status", "Wini", "룸메이트가 [%s]으로 상태를 변경했어요."),
    ;

    private final String type;
    private final String title;
    private final String body;

    public String formatBody(String arg) {
        return String.format(this.body, arg);
    }
}
