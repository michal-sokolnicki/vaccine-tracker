package com.vaccinetracker.elastic.model.util;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class IndexModelUtil {

    public static final DateTimeFormatter BOOKING_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            .withLocale(Locale.ENGLISH);
}
