package com.company.calculator.utils;

import com.company.calculator.exceptions.InvalidHolidaysJSONException;
import com.company.calculator.exceptions.WrongDatesException;
import com.company.calculator.exceptions.WrongVacationDaysException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class Calendar {

    private final String holidaysJSON;

    // хранение JSON, чтобы каждый раз не загружать его при запросе пользователя
    public Calendar() {
        holidaysJSON = getJSON();
    }

    // JSON содержит список месяцев с указанием праздничных дней и выходных (СБ, ВС)
    private String getJSON() {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> resp =
                restTemplate.getForEntity("https://xmlcalendar.ru/data/ru/2024/calendar.json", String.class);

        // проверка, что запрос завершился успешно
        String result = resp.getStatusCode() == HttpStatus.OK ? resp.getBody() : null;
        if (result != null && !result.isEmpty()) {
            return result;
        } else {
            throw new InvalidHolidaysJSONException("Возникла ошибка при получении JSON списка с месяцами и выходными");
        }
    }

    public int getHolidaysCount(String start, String end, int vacationDays) throws JsonProcessingException {
        // перевод строк в массив чисел, чтобы дальше сравнить даты
        int[] startArr = formatDate(start);
        int[] endArr = formatDate(end);

        int startMonth = startArr[1];
        int startDay = startArr[2];

        int endMonth = endArr[1];
        int endDay = endArr[2];

        if (startMonth > endMonth || (startMonth == endMonth && startDay > endDay)) {
            throw new WrongDatesException("Дата начала отпуска позже, чем дата окончания отпуска");
        }

        // вычисление реального количества дней между датами
        // и сравнение с введенным пользователем количеством дней отпуска
        int allDays = getAllDays(start, end);
        if (allDays != vacationDays) {
            throw new WrongVacationDaysException("Указанное количество дней отпуска превышает количество дней между датами");
        }

        // получение списка месяцев из JSON
        List<Month> monthList = getMonthList();
        int holidays = 0;

        // если даты находятся в одном месяце,
        // то нужно посчитать сколько праздничных дней между первым и последним днем отпуска
        // (первый и последний день включительно, так как они тоже могут быть выходными)
        if (startMonth == endMonth) {
            int[] days = formatDays(monthList.get(startMonth - 1).getDays());
            for (int day : days) {
                if (startDay <= day && day <= endDay) {
                    holidays += 1;
                }
            }
        } else {
            // если дата начала отпуска и окончания в разных месяцах,
            // то сначала находит количество выходных в первом месяце,
            // а потом в последнем
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

            // если между первым и втором месяцем есть еще месяцы,
            // то нужно посчитать выходные и в этих месяцах
            if (startMonth != endMonth - 1) {
                for (int month = startMonth; month < endMonth - 1; month++) {
                    String[] days = monthList.get(month).getDays().split(",");
                    holidays += days.length;
                }
            }
        }
        // возвращает количество выходных между датами начала и окончания отпуска
        return holidays;
    }

    // метод для преобразования даты к массиву чисел
    private int[] formatDate(String date) {
        String[] dateStrArr = date.split("-");
        return toIntArray(dateStrArr);
    }

    // в JSON некоторые выходные дни записаны как
    // 22*, 22+
    // поэтому такой метод для перехода от строки со днями к массиву чисел
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

    // получения из JSON списка с месяцами и выходными днями
    private List<Month> getMonthList() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        CalenderObject calenderObject = mapper.readValue(holidaysJSON, CalenderObject.class);
        return calenderObject.getMonths();
    }

    // получение всех дней от первого дня отпуска до последнего (первый и последний день включительно)
    private int getAllDays(String start, String end) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(start, formatter);
        LocalDate endDate = LocalDate.parse(end, formatter);
        long allDays = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate) + 1;
        // максимальное количество дней в отпуске 180 (из CalcController)
        return (int) allDays;
    }



}
