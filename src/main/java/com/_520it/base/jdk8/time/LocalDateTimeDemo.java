package com._520it.base.jdk8.time;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjusters;
import java.util.Set;

public class LocalDateTimeDemo {

    /**
     * LocalDate,LocalTime,LocalDateTime
     */
    @Test
    public void test1(){
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);
        LocalTime localTime = LocalTime.now();
        System.out.println(localTime);
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);
        System.out.println("------------------------");
        LocalDateTime ldt = LocalDateTime.of(2020, 6, 1, 14, 31,30);
        System.out.println(ldt);

        LocalDateTime ldt2 = ldt.plusYears(10);
        System.out.println(ldt2);

        LocalDateTime ldt3 = ldt.minusMonths(2);
        System.out.println(ldt3);

        System.out.println(ldt.getYear());
        System.out.println(ldt.getMonthValue());
        System.out.println(ldt.getDayOfMonth());
        System.out.println(ldt.getHour());
        System.out.println(ldt.getMinute());
        System.out.println(ldt.getSecond());
    }

    /**
     * Instant : 时间戳（使用 Unix 元年  1970年1月1日 00:00:00 所经历的毫秒值）
     */
    @Test
    public void test2(){
        //默认使用 UTC 时区
        Instant ins = Instant.now();
        System.out.println(ins);

        OffsetDateTime dateTime = ins.atOffset(ZoneOffset.ofHours(8));
        System.out.println(dateTime);
        System.out.println(dateTime.getNano());
        Instant instant = Instant.ofEpochSecond(5);
        System.out.println(instant);

    }

    /**
     *
     * Duration : 用于计算两个“时间”间隔
     * Period : 用于计算两个“日期”间隔
     */
    @Test
    public void test3(){
        Instant instant = Instant.now();

        System.out.println("-----------");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Instant instant1 = Instant.now();
        System.out.println("所消耗的时间为:"+Duration.between(instant,instant1));
        System.out.println("----------------------------------");

        LocalDate ld1 = LocalDate.now();
        LocalDate ld2 = LocalDate.of(2008, 8, 8);

        Period period = Period.between(ld2,ld1);
        System.out.println(period.getYears());
        System.out.println(period.getMonths());
        System.out.println(period.getDays());
    }

    /**
     * 4. TemporalAdjuster : 时间校正器
     */
    @Test
    public void test4(){
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);

        LocalDateTime ldt2 = ldt.withDayOfMonth(10);
        System.out.println(ldt2);

        LocalDateTime ldt3 = ldt.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        System.out.println(ldt3);

        //自定义：下一个工作日
        LocalDateTime localDateTime = ldt.with((l) -> {
            LocalDateTime ldt4 = (LocalDateTime) l;
            DayOfWeek day = ldt4.getDayOfWeek();
            if (day.equals(DayOfWeek.FRIDAY)) {
                return ldt.plusDays(3);
            } else if (day.equals(DayOfWeek.SATURDAY)) {
                return ldt.plusDays(2);
            } else {
                return ldt.plusDays(1);
            }
        });

        System.out.println(localDateTime);
    }

    /**
     * 5. DateTimeFormatter : 解析和格式化日期或时间
     */
    @Test
    public void test5(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss E");
        LocalDateTime ldt = LocalDateTime.now();
        String s = dtf.format(ldt);
        System.out.println(s);
        System.out.println(ldt.format(dtf));

        LocalDateTime newLdt = LocalDateTime.parse(s, dtf);
        System.out.println(newLdt);
    }

    @Test
    public void test6(){
        Set<String> set = ZoneId.getAvailableZoneIds();
        set.forEach(System.out::println);
    }


    //6.ZonedDate、ZonedTime、ZonedDateTime ： 带时区的时间或日期
    @Test
    public void test7(){
        LocalDateTime ldt = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        System.out.println(ldt);

        ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("US/Pacific"));
        System.out.println(zdt);
    }
}
