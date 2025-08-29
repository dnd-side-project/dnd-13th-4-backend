package com.example.wini.domain.member.dto.common;

import static com.example.wini.global.common.constant.StatusReserveTimeConstants.INDEFINITE_HOUR;
import static com.example.wini.global.common.constant.StatusReserveTimeConstants.INDEFINITE_MINUTE;
import static com.example.wini.global.common.constant.StatusReserveTimeConstants.INDEFINITE_SECONDS;
import static com.example.wini.global.common.constant.StatusReserveTimeConstants.SECONDS_PER_HOUR;
import static com.example.wini.global.common.constant.StatusReserveTimeConstants.SECONDS_PER_MINUTE;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ReservedTimeInfo(
        @NotNull @Min(value = -1) @Max(value = 12) Long hour, @NotNull @Min(value = -1) @Max(value = 59) Long minute) {
    public static ReservedTimeInfo of(long hour, long minute) {
        return new ReservedTimeInfo(hour, minute);
    }

    public long toSeconds() {
        if (this.hour == INDEFINITE_HOUR && this.minute == INDEFINITE_MINUTE) {
            return INDEFINITE_SECONDS;
        }
        return this.hour * SECONDS_PER_HOUR + this.minute * SECONDS_PER_MINUTE;
    }
}
