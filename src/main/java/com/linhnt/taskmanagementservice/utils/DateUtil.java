package com.linhnt.taskmanagementservice.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public final class DateUtil {
    private DateUtil() {
    }

    public static LocalDateTime parseLocalDateTime(String datetimeAsString, String pattern) {
        try {
            return LocalDateTime.parse(datetimeAsString, DateTimeFormatter.ofPattern(pattern));
        } catch (Exception e) {
            return null;
        }
    }

    public static LocalDate parseLocalDate(String dateAsString, String pattern) {
        try {
            return LocalDate.parse(dateAsString, DateTimeFormatter.ofPattern(pattern));
        } catch (Exception e) {
            return null;
        }
    }

    public static LocalTime parseLocalTime(String timeAsString, String pattern) {
        try {
            return LocalTime.parse(timeAsString, DateTimeFormatter.ofPattern(pattern));
        } catch (Exception e) {
            return null;
        }
    }

    public static String formatDateTime(LocalDate localDate, LocalTime localTime) {
        return formatDateTime(localDate, localTime, DateTimePattern.YYYYMMDDHHMM);
    }

    public static String formatDate(LocalDateTime localDate, String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(localDate);
    }

    public static String formatHhMmSs(LocalDateTime localDate, String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(localDate);
    }

    public static String formatDateTime(LocalDate localDate, LocalTime localTime, String pattern) {
        return formatDateTime(LocalDateTime.of(localDate, localTime), pattern);
    }

    public static String formatDateTime(LocalDateTime localDateTime, String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(localDateTime);
    }

    public static String formatCurrentDateTime(String pattern) {
        long currentTimeMillis = System.currentTimeMillis();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(currentTimeMillis), ZoneId.systemDefault()).format(formatter);
    }

    public static String convertZone(String datetime, String datePattern, ZoneId fromZoneId, ZoneId toZoneId) {
        if (fromZoneId.equals(toZoneId)) {
            return datetime;
        }
        LocalDateTime localDateTime = parseLocalDateTime(datetime, datePattern);
        if (localDateTime == null) {
            return null;
        }
        return DateTimeFormatter.ofPattern(datePattern)
                .format(localDateTime.atZone(fromZoneId).withZoneSameInstant(toZoneId));
    }

    public static LocalDateTime convertUnixTimeToLocalDateTime(long unixTime) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(unixTime), ZoneId.systemDefault());
    }

    public static long convertLocalDateTimeToUnixTime(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    // Convert date from yyyyMMdd to String pattern
    public static String convertDate(String yyyymmdd, String pattern) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(DateTimePattern.YYYYMMDD);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(pattern);
        LocalDate date = LocalDate.parse(yyyymmdd, inputFormatter);
        return date.format(outputFormatter);
    }

    public static LocalDate getDefaultDateStart(Integer month) {
        return LocalDate.now().minusMonths(month);
    }
}
