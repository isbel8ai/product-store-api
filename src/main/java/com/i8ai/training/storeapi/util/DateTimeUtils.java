package com.i8ai.training.storeapi.util;

import java.util.Date;

public class DateTimeUtils {
    public static final Date DATE_MIN = new Date(0);
    public static final Date DATE_MAX = new Date();

    public static Date dateOrMin(Date start) {
        return start != null ? start : DateTimeUtils.DATE_MIN;
    }

    public static Date dateOrMax(Date end) {
        return end != null ? end : DateTimeUtils.DATE_MAX;
    }
}
