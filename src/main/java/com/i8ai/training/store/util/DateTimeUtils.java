package com.i8ai.training.store.util;

import java.util.Date;

public class DateTimeUtils {

    private DateTimeUtils() {
    }

    public static Date dateOrMin(Date start) {
        return start != null ? start : new Date(0);
    }

    public static Date dateOrMax(Date end) {
        return end != null ? end : new Date();
    }
}
