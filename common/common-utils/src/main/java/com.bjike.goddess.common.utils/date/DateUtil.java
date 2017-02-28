package com.bjike.goddess.common.utils.date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author: [liguiqin]
 * @Date: [2017-01-13 10:42]
 * @Description: [日期工具类]
 * @Version: [1.0.0]
 * @Copy: [com.bjike]
 */
public class DateUtil {
    private static final DateTimeFormatter DATETIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm:ss");

    /**
     * 日期时间转换
     *
     * @param dateTime
     * @return
     */
    public static LocalDateTime parseDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, DATETIME);
    }

    /**
     * 日期转换
     *
     * @param date
     * @return
     */

    public static LocalDate parseDate(String date) {
        return LocalDate.parse(date, DATE);
    }

    /**
     * 时间转换
     *
     * @param time
     * @return
     */
    public static LocalTime parseTime(String time) {
        return LocalTime.parse(time, TIME);
    }

    /**
     * 时间转相应字符串
     * @param time
     * @return
     */
    public static String timeToString(LocalTime time) {
        return time.format(TIME);
    }
    /**
     * 日期时间转相应字符串
     * @param dateTime
     * @return
     */
    public static String datetimeToString(LocalDateTime dateTime) {
        return dateTime.format(DATETIME);
    }
    /**
     * 日期转相应字符串
     * @param date
     * @return
     */
    public static String dateToString(LocalDate date) {
        return date.format(DATE);
    }

}