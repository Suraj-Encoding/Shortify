package com.shortify.util;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Time Utilities
 */
public class TimeUtils {

    private static final String IST_TIMEZONE = "Asia/Kolkata";

    /**
     * Get current time in IST timezone
     */
    public static LocalDateTime getCurrentTimeInIST() {
        return LocalDateTime.now(ZoneId.of(IST_TIMEZONE));
    }
}
