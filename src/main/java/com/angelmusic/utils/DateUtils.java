package com.angelmusic.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ningxuwen on 2016/12/30.
 */
public class DateUtils {

    /**
     * 获取当前主题
     * @param start
     * @param end
     * @return
     */
    public static int getTheme(Date start, Date end) {
        if (start.after(end)) {
            Date t = start;
            start = end;
            end = t;
        }

        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);

        int year = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
        int month = endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);

        int themeCount = (year * 12 + month - 1) < 0 ? 0 : (year * 12 + month);

        int d1 = endCalendar.get(Calendar.DAY_OF_MONTH);
        int d2 = startCalendar.get(Calendar.DAY_OF_MONTH);

        //判断天
        if(d1 >= d2){
            themeCount += 1;
        }
        return themeCount;
    }
}
