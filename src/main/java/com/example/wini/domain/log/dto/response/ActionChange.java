package com.example.wini.domain.log.dto.response;

import com.example.wini.domain.template.domain.Action;
import lombok.Getter;

@Getter
public class ActionChange {

    private Action action;
    private Long change;

    //    @QueryProjection
    public ActionChange(Action action, Long change) {
        this.action = action;
        this.change = change;
    }
}
