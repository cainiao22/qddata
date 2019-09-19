package com.qding.bigdata.ds.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

public class DateUtil {

  public static final String YYYY_MM_DD = "yyyy-MM-dd";
  public static final String YYYY_MM = "yyyy-MM";
  public static final String YYYYMMDD = "yyyyMMdd";
  public static final String YYYYMMDDHHMM = "yyyyMMddHHmm";


  /**
   * 获取相差天数
   *
   * @param big
   * @param small
   * @return
   */
  public static Integer getDayDiff(Date big, Date small) {
    if (big != null && small != null) {
      long sub = big.getTime() - small.getTime();
      long day = 1000L * 60 * 60 * 24;
      int presume = (int) (sub / day);

      Calendar bigCalendar = Calendar.getInstance();
      bigCalendar.setTime(big);
      Calendar cursor = Calendar.getInstance();
      cursor.setTime(small);

      cursor.add(Calendar.DAY_OF_YEAR, presume);

      if (cursor.get(Calendar.DAY_OF_YEAR) == bigCalendar.get(Calendar.DAY_OF_YEAR)) {
        return presume;
      } else {
        return presume + 1;
      }

    }
    return null;
  }

  /**
   * 日期字符串转换 2012-12-21 -> 20121221
   *
   * @param date
   * @return
   */
  public static String standardDateToNumericDate(String date) {
    return date.replaceAll("-", "");
  }

  /**
   * 日期字符串转换 20121221 -> 2012-12-21
   *
   * @param date
   * @return
   */
  public static String numericDateToStandardDate(String date) {
    StringBuilder builder = new StringBuilder();
    builder.append(date.substring(0, 4)).append("-").append(date.substring(4, 6)).append("-")
        .append(date.substring(6));
    return builder.toString();
  }

  public static String formatDateTString(Date date) {
    SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
    return f.format(date);
  }

  public static String formatDateToFullString(Date date) {
    SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmm");
    return f.format(date);
  }

  public static String formatDateToDTString(Date date) {
    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    return f.format(date);
  }

  public static String formatDateToFullString2(Date date) {
    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return f.format(date);
  }

  /**
   * 增加分钟，可以为正负值
   *
   * @param date Date
   * @param minute 分钟数可以为正负值
   * @return Date
   */
  public static Date addMinutes(final Date date, final int minute) {
    if (null == date) {
      return null;
    }
    final Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.add(Calendar.MINUTE, minute);
    final Date end = c.getTime();
    return end;
  }

  /**
   * 增加分钟，可以为正负值
   *
   * @param date Date
   * @param minuts 分钟数可以为正负值
   * @return Date
   */
  public static List<String> getMinutes(final Date date, final int minute) {

    List<String> list = new ArrayList<String>();

    final Calendar c = Calendar.getInstance();
    int min = minute <= 0 ? minute : 0;
    int max = minute <= 0 ? -1 : minute;
    for (int i = min; i <= max; i++) {
      c.setTime(date);
      c.add(Calendar.MINUTE, i);
      list.add(formatDateToFullString(c.getTime()));
    }
    return list;
  }

  /**
   * 增加分钟，可以为正负值
   *
   * @param date Date
   * @param minuts 分钟数可以为正负值
   * @return Date
   */
  public static List<String> getAllMinutes(final Date date) {

    List<String> list = new ArrayList<String>();

    final Calendar c = Calendar.getInstance();
    for (int i = 0; i < 1440; i++) {
      c.setTime(date);
      c.add(Calendar.MINUTE, i);
      list.add(formatDateToFullString(c.getTime()));
    }
    return list;
  }

  /**
   * 增加分钟，可以为正负值
   *
   * @param minuts 分钟数可以为正负值
   * @return Date
   */
  public static List<String> getMinutesFromNow(final int minute) {
    return getMinutes(new Date(), minute);
  }

  /**
   * Description:获取今天到目前的所有分钟字符串
   *
   * @return
   */
  public static List<String> getMinutesFromNowLimitToday() {
    final Calendar c = Calendar.getInstance();
    return getMinutesFromNow((c.get(Calendar.HOUR_OF_DAY) * 60 + c.get(Calendar.MINUTE)) * -1);
  }

  public static String getDayByN(int n, String format) {
    Calendar c = Calendar.getInstance();
    DateFormat df = new SimpleDateFormat(format);
    c.setTime(new Date());
    c.add(Calendar.DATE, n);
    Date d2 = c.getTime();
    String s = df.format(d2);
    return s;
  }

  public static String getDayByN(Date dt, int n, String format) {
    Calendar c = Calendar.getInstance();
    DateFormat df = new SimpleDateFormat(format);
    c.setTime(dt);
    c.add(Calendar.DATE, n);
    Date d2 = c.getTime();
    String s = df.format(d2);
    return s;
  }

  public static Date getDateByNDay(Date dt, int n) {
    Calendar c = Calendar.getInstance();
    c.setTime(dt);
    c.add(Calendar.DATE, n);
    Date d2 = c.getTime();
    return d2;
  }

  public static String getDayByN(int n) {
    return getDayByN(n, YYYY_MM_DD);
  }

  public static String getDayByN(Date dt, int n) {
    return getDayByN(dt, n, YYYY_MM_DD);
  }

  public static Date parseStr2Date(String day) {
 return parseStr2Date(day,YYYY_MM_DD);
  }
  public static Date parseStr2Date(String day,String format) {
	  DateFormat df = new SimpleDateFormat(format);
	  try {
		  return df.parse(day);
	  } catch (ParseException e) {
		  e.printStackTrace();
	  }
	  return null;
  }

  public static Date parseMinuteStr2Date(String minute) {
    DateFormat df = new SimpleDateFormat(YYYYMMDDHHMM);
    try {
      return df.parse(minute);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static long parseMinuteStr2Second(String minute) {
 return parseMinuteStr2Date(minute).getTime()/1000;
  }

  public static String formatDateByMilliseconds(long milliseconds) {
    return formatDateToFullString2(new Date(milliseconds));
  }

  public static String formatDateByMillisecondsUDF(long milliseconds,String format) {
    Date date =  new Date(milliseconds);
    SimpleDateFormat f = new SimpleDateFormat(format);
    return f.format(date);
  }

  public static String getMonthByN(Date dt, int n) {
    return getMonthByN(dt, n, YYYY_MM);
  }
  public static String getMonthByN(Date dt, int n, String format) {
    Calendar c = Calendar.getInstance();
    DateFormat df = new SimpleDateFormat(format);
    c.setTime(dt);
    c.add(Calendar.MONTH, n);
    Date d2 = c.getTime();
    String s = df.format(d2);
    return s;
  }

  public static Date getDateByNMonth(Date dt, int n) {
    Calendar c = Calendar.getInstance();
    c.setTime(dt);
    c.add(Calendar.MONTH, n);
    Date d2 = c.getTime();
    return d2;
  }

  public static String getHourByN(Date dt, int n) {
    Calendar c = Calendar.getInstance();
    c.setTime(dt);
    c.add(Calendar.HOUR_OF_DAY, n);
    int hour = c.get(Calendar.HOUR_OF_DAY);

    return String.format("%02d", hour);
  }


  public static void main(String[] args) {
    long start = System.currentTimeMillis();
    List<String> allMinutes = getAllMinutes(DateUtil.parseStr2Date("20160508"));
    System.out.println(allMinutes);
    long end = System.currentTimeMillis();
    System.out.println(end - start);
  }


  /**
   * 获取两个日期间隔的所有日期
   * @param start 格式必须为'2018-01-25'
   * @param end 格式必须为'2018-01-25'
   * @return
   */
  public static List<String> getBetweenDate(String start, String end){
    List<String> list = new ArrayList<>();
    LocalDate startDate = LocalDate.parse(start);
    LocalDate endDate = LocalDate.parse(end);

    long distance = ChronoUnit.DAYS.between(startDate, endDate);
    if (distance < 1) {
      return list;
    }
    Stream.iterate(startDate, d -> {
      return d.plusDays(1);
    }).limit(distance + 1).forEach(f -> {
      list.add(f.toString());
    });
    return list;
  }

  public static long getBetweenDayNum(String start, String end){
    LocalDate startDate = LocalDate.parse(start);
    LocalDate endDate = LocalDate.parse(end);
    long distance = ChronoUnit.DAYS.between(startDate, endDate);
    return distance;
  }

  /**
   * 获取两个日期之间的日期列表
   * @param minDate
   * @param maxDate
   * @return
   * @throws ParseException
   */
  public static List<String> getMonthBetween(String minDate, String maxDate) throws ParseException {
    ArrayList<String> result = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月

    Calendar min = Calendar.getInstance();
    Calendar max = Calendar.getInstance();

    min.setTime(sdf.parse(minDate));
    min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

    max.setTime(sdf.parse(maxDate));
    max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

    Calendar curr = min;
    while (curr.before(max)) {
      result.add(sdf.format(curr.getTime()));
      curr.add(Calendar.MONTH, 1);
    }

    return result;
  }

  /**
   *
   * @param minDate
   * @param maxDate
   * @return
   */
  public static int getMonthDistance(Date minDate, Date maxDate) {
    Calendar min = Calendar.getInstance();
    Calendar max = Calendar.getInstance();
    min.setTime(minDate);

    max.setTime(maxDate);
    return (max.get(Calendar.YEAR) - min.get(Calendar.YEAR)) * 12 + (max.get(Calendar.MONTH) - min.get(Calendar.MONTH));
  }

  /**
   * 比较两个日期是否相等，忽略时分秒
   * @param dt1
   * @param dt2
   * @return
   */
  public static int compareDate(Date dt1, Date dt2){
    DateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD);
    String dt1Str = dateFormat.format(dt1);
    String dt2Str = dateFormat.format(dt2);
    return dt1Str.compareTo(dt2Str);
  }

  public static int compareMonth(Date dt1, Date dt2){
    DateFormat dateFormat = new SimpleDateFormat(YYYY_MM);
    String dt1Str = dateFormat.format(dt1);
    String dt2Str = dateFormat.format(dt2);
    return dt1Str.compareTo(dt2Str);
  }

  public static List<String> getUntilHour(int untilHour) {
    List<String> hourList = new ArrayList<>();
    for(int i=0; i<=untilHour; i++){
      hourList.add(String.format("%02d", i));
    }

    return hourList;
  }

  /**
   * 判断当前time是否是当月 当日
   * @param time
   * @param pattern yyyy-MM-dd | yyyy-MM
   * @return
   */
  public static boolean isThisTime(long time, String pattern) {
    Date date = new Date(time);
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    String param = sdf.format(date);//参数时间
    String now = sdf.format(new Date());//当前时间
    if(param.equals(now)){
      return true;
    }
    return false;
  }

  public static LocalDate date2LocalDateTime(Date date){
    Instant instant = date.toInstant();
    ZoneId zoneId = ZoneId.systemDefault();
    // atZone()方法返回在指定时区从此Instant生成的ZonedDateTime。
    return instant.atZone(zoneId).toLocalDate();
  }

  public static  Date localDateTime2Date( LocalDate localDate){
    ZoneId zoneId = ZoneId.systemDefault();
    ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
    Date date = Date.from(zdt.toInstant());
    return date;
  }
}
