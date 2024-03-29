package com.company.calculator.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class Calendar {

    public static int getHolidayDaysCount(String start, String end) throws JsonProcessingException {
        String[] startArr = start.split("-");
        String[] endArr = end.split("-");

        int startMonth = Integer.parseInt(startArr[1]);
        int startDay = Integer.parseInt(startArr[2]);

        int endMonth = Integer.parseInt(endArr[1]);
        System.out.println(Arrays.toString(endArr));
        int endDay = Integer.parseInt(endArr[2]);

        System.out.println("start month");
        System.out.println(startMonth);
        System.out.println("start day");
        System.out.println(startDay);
        System.out.println("end month");
        System.out.println(endMonth);
        System.out.println("end day");
        System.out.println(endDay);

        ObjectMapper mapper = new ObjectMapper();
        CalenderObject calenderObject = mapper.readValue(getHolidayDaysJSON(), CalenderObject.class);
        List<Month> months = calenderObject.getMonths();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(start, formatter);
        LocalDate endDate = LocalDate.parse(end, formatter);
        long allDays = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate) + 1;

        System.out.println("all days");
        System.out.println(allDays);
        int holidays = 0;

        if (startMonth == endMonth) {
            int[] days = fromStringToIntArray(months.get(startMonth - 1).getDays());
            System.out.println("holidays in month");
            System.out.println(Arrays.toString(days));
            for (int day : days) {
                if (startDay <= day && day <= endDay) {
                    System.out.println(day);
                    //allDays -= 1;
                    holidays += 1;
                }
            }
        } else {
            int[] startMonthDays = fromStringToIntArray(months.get(startMonth - 1).getDays());
            for (int startMonthDay : startMonthDays) {
                if (startDay <= startMonthDay) {
                    System.out.println("1 month");
                    System.out.println(startMonthDay);
                    //allDays -= 1;
                    holidays += 1;
                }
            }
            int[] endMonthDays = fromStringToIntArray(months.get(endMonth - 1).getDays());
            for (int endMonthDay : endMonthDays) {
                if (endMonthDay <= endDay) {
                    //allDays -= 1;
                    System.out.println("2 month");
                    System.out.println(endMonthDay);
                    holidays += 1;
                }
            }

            if (startMonth != endMonth - 1) {
                for (int month = startMonth; month < endMonth - 1; month++) {
                    String[] days = months.get(month).getDays().split(",");
                    //allDays -= days.length;
                    holidays += days.length;
                }
            }
        }
        System.out.println("holidays");
        System.out.println(holidays);
        return holidays;
    }

    private static int[] fromStringToIntArray(String days) {
        days = days.replace("*", "");
        days = days.replace("+", "");
        String[] daysStringArr = days.split(",");
        int len = daysStringArr.length;
        int[] daysIntArr = new int[len];
        for (int i = 0; i < len; i++) {
            daysIntArr[i] = Integer.parseInt(daysStringArr[i]);
        }
        return daysIntArr;
    }

    private static String getHolidayDaysJSON() {
        RestClient restClient = RestClient.create();

        String result = restClient.get()
                .uri("https://xmlcalendar.ru/data/ru/2024/calendar.json")
                .retrieve()
                .body(String.class);

        //System.out.println(result);

        return result;
    }

}
