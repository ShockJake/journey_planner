package io.jp.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter INPUT_DATE_TIME_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;
    private static final DateTimeFormatter OUTPUT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm");

    public static String getDate(LocalDateTime localDateTime) {
        return DATE_FORMATTER.format(localDateTime);
    }

    public static String toOutputDateTime(String dateTime) {
        return OUTPUT_DATE_TIME_FORMATTER.format(INPUT_DATE_TIME_FORMATTER.parse(dateTime));
    }
}
