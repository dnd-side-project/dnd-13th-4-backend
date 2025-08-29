package com.example.wini.domain.note.domain;

import static com.example.wini.global.error.exception.ErrorCode.SORT_ORDER_NOT_FOUND;

import com.example.wini.global.error.exception.CustomException;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortOrder {
    ASC("oldest"),
    DESC("latest"),
    ;

    private final String value;

    public static SortOrder from(String value) {
        return Arrays.stream(SortOrder.values())
                .filter(sortOrder -> sortOrder.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new CustomException(SORT_ORDER_NOT_FOUND));
    }
}
