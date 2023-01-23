package com.ming.facetocode.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author 小明
 * @date 2021/10/19
 * @description
 */
public class DateUtil {
    public static String getCurrentTime() {
        // 格式化时间
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
        //获取当前时间
        Date date = new Date();
        return sdf.format(date);
    }

    public static Date getCurrentDate(){
        return new Date();
    }

    public static int getSecond() {

        int length = getCurrentTime().length();
        String s = getCurrentTime().substring(length - 2);
        return Integer.parseInt(s);
    }

    public static String getWeek(int n){
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime localDateTime = now.minusDays(n);
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd ");
        final String format = dateTimeFormatter.format(localDateTime);
        return format;
    }
}
