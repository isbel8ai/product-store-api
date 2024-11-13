package com.i8ai.training.store.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Optional;

@UtilityClass
public class DateTimeUtils {

    public static final LocalDateTime MIN_DATETIME = LocalDateTime.of(0, 1, 1, 0, 0);
    public static final LocalDateTime MAX_DATETIME = LocalDateTime.of(10000, 1, 1, 0, 0);

    public LocalDateTime dateTimeOrMin(LocalDateTime localDateTime) {
        return Optional.ofNullable(localDateTime).orElse(MIN_DATETIME);
    }

    public LocalDateTime dateTimeOrMax(LocalDateTime localDateTime) {
        return Optional.ofNullable(localDateTime).orElse(MAX_DATETIME);
    }

    public LocalDateTime dateTimeOrMin(ZonedDateTime zonedDateTime) {
        return Optional.ofNullable(zonedDateTime)
                .map(value -> value.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime())
                .orElse(MIN_DATETIME);
    }

    public LocalDateTime dateTimeOrNow(ZonedDateTime zonedDateTime) {
        return Optional.ofNullable(zonedDateTime)
                .map(value -> value.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime())
                .orElse(LocalDateTime.now());
    }

    public LocalDateTime dateTimeOrMax(ZonedDateTime zonedDateTime) {
        return Optional.ofNullable(zonedDateTime)
                .map(value -> value.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime())
                .orElse(MAX_DATETIME);
    }

    public ZonedDateTime toUtcDateTime(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC);
    }
}
