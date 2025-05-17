package io.jp.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String getDate(LocalDateTime localDateTime) {
        return DATE_FORMATTER.format(localDateTime);
    }
}
