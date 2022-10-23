package com.procedure.manager.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils {

    public static LocalDateTime getInitialLocalDateTime(int month, int year) {
        return LocalDateTime.of(year, month, 1, 0, 0, 0);
    }

    public static LocalDateTime getFinalLocalDateTime(int month, int year) {
        return LocalDateTime.of(year, month, 1, 23, 59, 59).with(TemporalAdjusters.lastDayOfMonth());
    }

}
