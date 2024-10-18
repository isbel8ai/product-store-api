package com.i8ai.training.store.util;

import lombok.experimental.UtilityClass;

import java.util.Date;

@UtilityClass
public class DateTimeUtils {

    public Date dateOrMin(Date date) {
        return date != null ? date : new Date(0);
    }

    public Date dateOrNow(Date date) {
        return date != null ? date : new Date();
    }
}
