package com.i8ai.training.store.util;

import lombok.experimental.UtilityClass;

import java.util.Date;

@UtilityClass
public class DateTimeUtils {

    public Date dateOrMin(Date start) {
        return start != null ? start : new Date(0);
    }

    public Date dateOrMax(Date end) {
        return end != null ? end : new Date();
    }
}
