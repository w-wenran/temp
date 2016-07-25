package org.base.utils;

import org.reststackteam.reststack.exception.RuntimeExceptionWarning;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 时间相关的工具类.
 */
public class TimeUtil {

    public static SimpleDateFormat YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");

    public static SimpleDateFormat YYYY_MM_DD_HH_mm_ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final Integer[] WEEK_NUMBER = {7,1,2,3,4,5,6};

    public static Date getIntervalTime(Integer days){
        if(days == null){
            days = 0;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE,days);
        return calendar.getTime();
    }

    public static long[] differTimes(Date start,Date end){

        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;

        long time1 = start.getTime();
        long time2 = end.getTime();
        long diff ;
        if(time1<time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        day = diff / (24 * 60 * 60 * 1000);
        hour = diff / (60 * 60 * 1000);
        min = diff / (60 * 1000);
        sec = diff/1000;

        long[] times = {day, hour, min, sec};
        return times;
    }

    /**
     * 获取起始时间
     * @param time
     * @return
     */
    public static Date getTimeStart(String time){
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(YYYY_MM_DD.parse(time));
            calendar.set(Calendar.HOUR_OF_DAY,0);
            calendar.set(Calendar.SECOND,0);
            calendar.set(Calendar.MINUTE,0);
            return calendar.getTime();
        } catch (Exception e) {
            throw new RuntimeExceptionWarning(ServerStatus.PARAM_IS_FAIL,"时间格式有误");
        }
    }

    /**
     * 获取结束时间
     * @param time
     * @return
     */
    public static Date getTimeEnd(String time){
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(YYYY_MM_DD.parse(time));
            calendar.set(Calendar.HOUR_OF_DAY,23);
            calendar.set(Calendar.SECOND,59);
            calendar.set(Calendar.MINUTE,59);
            return calendar.getTime();
        } catch (Exception e) {
            throw new RuntimeExceptionWarning(ServerStatus.PARAM_IS_FAIL,"时间格式有误");
        }
    }

    /**
     * 校验时间段是否合法
     * @param start
     * @param end
     * @return 起始时间>结束时间返回true 否则返回false
     */
    public static boolean validDatePeriod(Date start,Date end){
        if(StrUtil.isNull(start) || StrUtil.isNull(end)){
            throw new RuntimeExceptionWarning(ServerStatus.PARAM_IS_NULL,"时间信息不能为空");
        }
        return start.getTime()>end.getTime();
    }

    /**
     * 获取一天的开始时间 如2013-12-11 02:23 返回2013-12-11 00:00:00
     * @param date 处理的时间
     * @return
     */
    public static Date dateToStartTime(Date date){
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(date.getTime());
        gc.set(Calendar.HOUR_OF_DAY, 0);
        gc.set(Calendar.MINUTE,0);
        gc.set(Calendar.SECOND,0);
        return gc.getTime();
    }

    /**
     * 计算日期往后推几天的日期
     * @param afterDays 后推
     * @param date 计算的日期
     * @return Date
     */
    public static Date afterDate(int afterDays,Date date){
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(date.getTime());
        gc.add(Calendar.DATE, afterDays);
        return gc.getTime();
    }

    /**
     * 格式化时间为标准的格式串
     * @param date 日期时间
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String formatDate(Date date){
        return YYYY_MM_DD_HH_mm_ss.format(date);
    }
    /**
     * 格式化时间为标准的格式串
     * @param date 日期时间
     * @return yyyy-MM-dd
     */
    public static String formatDate1(Date date){
        return YYYY_MM_DD.format(date);
    }
    /**
     * 获取周以及对应的年份
     * @param date
     * @param preWeeks
     * @return
     */
    public static Integer[] yearAndWeek(Date date,int preWeeks){
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(date);
        calendar.add(Calendar.WEEK_OF_YEAR,-preWeeks);
        Integer week = calendar.get(Calendar.WEEK_OF_YEAR);
        Integer month = calendar.get(Calendar.MONTH)+1;
        if(week!=1 && month==1){
            //跨年周自动归并去年
            return new Integer[]{calendar.get(Calendar.YEAR)-1,week};
        }
        return new Integer[]{calendar.get(Calendar.YEAR),week};
    }

    public static Date afterMoth(Date date,int moths){
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(date);
        calendar.add(Calendar.MONTH,moths);
        return calendar.getTime();
    }

    /**
     * 周几
     * @param date
     * @return
     */
    public static Integer dayOfWeek(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    @Deprecated
    public static Integer totalDayOfYear(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
    }

    /**
     * 一年的第几周时间段
     * @param year
     * @param week
     * @return
     */
    public static Date[] dateWeekSegment(Integer year,Integer week){
        Date first = getTimeStart(year + "-" + "01-01 00:00:00");
        Integer dayOfWeek = dayOfWeek(first);
        int w = WEEK_NUMBER[dayOfWeek-1];
        if(w==1){
            //1月1号就是周一的时候
            w = 8;
        }
        int diff = 7-w;
        Date firstMonday = afterDate(diff + 1, first);
        Date weekStart = afterDate((week-1)*7,firstMonday);
        Date weekEnd = afterDate(7,weekStart);
        return new Date[]{weekStart,weekEnd};
    }

    /**
     * 获取当前日期年份
     * @param date
     * @return
     */
    public static  Integer year(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static Date getIntervalDay(Date date,int intervalDay){
        if(date==null){
            return date;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, intervalDay);
        return c.getTime();
    }

    public static  void main(String[] args){
        Date old=getTimeStart("2012-10-31 00:00:00");
        System.out.println(formatDate(afterMoth(old,1)));
    }
}
