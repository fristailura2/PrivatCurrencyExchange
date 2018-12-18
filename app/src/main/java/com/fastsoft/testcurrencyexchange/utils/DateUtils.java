package com.fastsoft.testcurrencyexchange.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateUtils {
    public static SimpleDateFormat dateFormat=new SimpleDateFormat("dd.MM.yyyy",Locale.getDefault());
    public static int getYear(String stringDate) throws ParseException {
        return get(stringDate,Calendar.YEAR);
    }
    public static int getDayOfMonth(String stringDate) throws ParseException {
        return get(stringDate,Calendar.DAY_OF_MONTH);
    }
    public static int getMonth(String stringDate) throws ParseException {
        return get(stringDate,Calendar.MONTH);
    }
    public static String getStringDate(int year,int month,int dayOfMonth){
        return dateFormat.format(getDate(year,month,dayOfMonth));
    }
    private static int get(String stringDate,int dateElement) throws ParseException {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(dateFormat.parse(stringDate));
        return calendar.get(dateElement);
    }
    public static String getStringDate(Date dateFrom) {
        return dateFormat.format(dateFrom);
    }
    public static Date getDate(int year,int month,int dayOfMonth){
        Calendar calendar=Calendar.getInstance();
        calendar.set(year,month,dayOfMonth);
        return calendar.getTime();
    }
    public static Date getDate(String stringDate) throws ParseException {
        return dateFormat.parse(stringDate);
    }
    public static List<Date> getDaysForRange(Date form, Date to){
        final long dayInMilles=TimeUnit.DAYS.toMillis(1);

        List<Date> res=new ArrayList<>();

        for (Date date = form; date.before(to); date = new Date(date.getTime()+dayInMilles))
            res.add(date);

        return res;
    }
    public static List<String> getDaysForRangeAsString(Date form, Date to){
        List<Date> dates=getDaysForRange(form,to);
        List<String> res=new ArrayList<>(dates.size());
        for (Date date:dates) {
            res.add(dateFormat.format(date));
        }
        return res;
    }


}
