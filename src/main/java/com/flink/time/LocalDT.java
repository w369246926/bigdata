package com.flink.time;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;

public class LocalDT {
    public static void main(String[] args) {
        String datatime = "2022-04-27T12:30:28";

        LocalDateTime parse = LocalDateTime.parse(datatime);
        System.out.println("转译后>>>"+parse);
        int year = parse.getYear();
        System.out.println("年份>>>>"+year);
        Month month = parse.getMonth();
        System.out.println("月份>>>>"+month);
        int monthValue = parse.getMonthValue();
        System.out.println("数字月份>>>>"+monthValue);
        int dayOfMonth = parse.getDayOfMonth();
        System.out.println("本月的第几天>>>>"+dayOfMonth);
        DayOfWeek day1 = parse.minusDays(2).getDayOfWeek();
        System.out.println("本周第1天>>>>"+day1);
        DayOfWeek day2 = parse.minusDays(1).getDayOfWeek();
        System.out.println("本周第2天>>>>"+day2);
        DayOfWeek day3 = parse.getDayOfWeek();
        System.out.println("本周第3天>>>>"+day3);
        DayOfWeek day4 = parse.plusDays(1).getDayOfWeek();
        System.out.println("本周第4天>>>>"+day4);
        DayOfWeek day5 = parse.plusDays(2).getDayOfWeek();
        System.out.println("本周第5天>>>>"+day5);
        DayOfWeek day6 = parse.plusDays(3).getDayOfWeek();
        System.out.println("本周第6天>>>>"+day6);
        DayOfWeek day7 = parse.plusDays(4).getDayOfWeek();
        System.out.println("本周第7天>>>>"+day7);
        int dayOfYear = parse.getDayOfYear();
        System.out.println("本年第几天>>>>"+dayOfYear);


    }
}
