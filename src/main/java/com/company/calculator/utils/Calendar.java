package com.company.calculator.utils;

import com.company.calculator.exceptions.WrongDatesException;
import com.company.calculator.exceptions.WrongVacationDaysException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class Calendar {

    private final String holidaysJSON;

    public Calendar() {
        holidaysJSON = getJSON();
    }

    private String getJSON() {
        RestClient restClient = RestClient.create();
        String result = restClient.get()
                .uri("https://xmlcalendar.ru/data/ru/2024/calendar.json")
                .retrieve()
                .body(String.class);
        return result;
    }

    public String getHolidaysJSON() {
        return holidaysJSON;
    }

    public int getHolidaysCount(String start, String end, int vacationDays) throws JsonProcessingException {
        int[] startArr = formatDate(start);
        int[] endArr = formatDate(end);

        int startMonth = startArr[1];
        int startDay = startArr[2];

        int endMonth = endArr[1];
        int endDay = endArr[2];

        if (startMonth > endMonth || (startMonth == endMonth && startDay > endDay)) {
            throw new WrongDatesException("Дата начала отпуска позже, чем дата окончания отпуска");
        }

        int allDays = getAllDays(start, end);
        if (allDays != vacationDays) {
            throw new WrongVacationDaysException("Указанное количество дней отпуска превышает количество дней между датами");
        }

        List<Month> monthList = getMonthList();
        int holidays = 0;

        if (startMonth == endMonth) {
            int[] days = formatDays(monthList.get(startMonth - 1).getDays());
            for (int day : days) {
                if (startDay <= day && day <= endDay) {
                    holidays += 1;
                }
            }
        } else {
            int[] startMonthDays = formatDays(monthList.get(startMonth - 1).getDays());
            for (int day : startMonthDays) {
                if (startDay <= day) {
                    holidays += 1;
                }
            }
            int[] endMonthDays = formatDays(monthList.get(endMonth - 1).getDays());
            for (int day : endMonthDays) {
                if (day <= endDay) {
                    holidays += 1;
                }
            }

            if (startMonth != endMonth - 1) {
                for (int month = startMonth; month < endMonth - 1; month++) {
                    String[] days = monthList.get(month).getDays().split(",");
                    holidays += days.length;
                }
            }
        }
        return holidays;
    }

    private int[] formatDate(String date) {
        String[] dateStrArr = date.split("-");
        return toIntArray(dateStrArr);
    }

    private int[] formatDays(String days) {
        days = days.replace("*", "");
        days = days.replace("+", "");
        String[] daysStringArr = days.split(",");
        return toIntArray(daysStringArr);
    }

    private int[] toIntArray(String[] strArray) {
        int len = strArray.length;
        int[] intArray = new int[len];
        for (int i = 0; i < len; i++) {
            intArray[i] = Integer.parseInt(strArray[i]);
        }
        return intArray;
    }

    private List<Month> getMonthList() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        CalenderObject calenderObject = mapper.readValue(getHolidaysJSON(), CalenderObject.class);
        return calenderObject.getMonths();
    }

    private int getAllDays(String start, String end) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(start, formatter);
        LocalDate endDate = LocalDate.parse(end, formatter);
        long allDays = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate) + 1;
        // максимальное количество дней в отпуске 180
        return (int) allDays;
    }



}
