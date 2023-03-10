package com.procedure.manager.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public static String getLocalDateFormatted(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return localDate.format(formatter);
    }

    public static String getLocalDateTimeFormattedNow() {
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyHHmmss");
        return ldt.format(dtf);
    }

}
