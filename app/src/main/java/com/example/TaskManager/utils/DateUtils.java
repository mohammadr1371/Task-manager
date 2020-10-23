package com.example.TaskManager.utils;

import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {

    public static Date getRandomDate(int startYear, int endYear) {
        GregorianCalendar gc = new GregorianCalendar();
        int year = randBetween(startYear, endYear);
        gc.set(gc.YEAR, year);
        int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
        gc.set(gc.DAY_OF_YEAR, dayOfYear);
        return gc.getTime();
    }

    public static int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    public static String getTime(Date date) {
        return android.text.format.DateFormat.format("HH:mm" , date.getTime()).toString();
    }
    public static String getDate(Date date) {
        return android.text.format.DateFormat.format("MM/dd/yyyy" , date.getTime()).toString();
    }
}
