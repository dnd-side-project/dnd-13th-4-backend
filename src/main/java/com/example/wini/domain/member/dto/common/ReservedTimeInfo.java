package com.example.wini.domain.member.dto.common;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;

public record ReservedTimeInfo(
        @NotNull @Min(value = -1) @Max(value = 12) Long hour, @NotNull @Min(value = -1) @Max(value = 59) Long minute) {
    public static ReservedTimeInfo of(long hour, long minute) {
        return new ReservedTimeInfo(hour, minute);
    }

    public Duration toDuration() {
        if (this.hour == -1 && this.minute == -1) {
            return Duration.ofDays(365L * 100);
        }

        return Duration.ofHours(this.hour).plusMinutes(this.minute);
    }
}
