package com.detection.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @fileName DateUtil.java
 * @author csk
 * @createTime 2017年3月2日 下午5:28:45
 * @version 1.0
 * @function
 */

public class DateUtil {

    @SuppressWarnings("deprecation")
    public static String getYearMonthDateByHyphen(Date date) {
        if(date == null){
            date = new Date();
        }
        String year = String.valueOf(date.getYear() + 1900);
        String month = String.valueOf(date.getMonth());
        String day = String.valueOf(date.getDate());
        return " " + year + "-" + month + "-" + day + " ";
    }

    @SuppressWarnings("deprecation")
    public static String getYearMonthDateByChinese(Date date) {
        if(date == null){
            date = new Date();
        }
        String year = String.valueOf(date.getYear() + 1900);
        String month = String.valueOf(date.getMonth());
        String day = String.valueOf(date.getDate());
        return " " + year + "年 " + month + "月 " + day + "日 ";
    }

}
