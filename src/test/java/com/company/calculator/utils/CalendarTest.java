package com.company.calculator.utils;

import com.company.calculator.exceptions.InvalidHolidaysJSONException;
import com.company.calculator.exceptions.WrongDatesException;
import com.company.calculator.exceptions.WrongVacationDaysException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CalendarTest {

    @InjectMocks
    private Calendar calendar;

    @Test
    public void Calendar_getHolidaysCount_ReturnInt() throws JsonProcessingException {
        int res1 = calendar.getHolidaysCount("2024-02-05", "2024-02-09", 5);
        int res2 = calendar.getHolidaysCount("2024-02-05", "2024-02-05", 1);
        int res3 = calendar.getHolidaysCount("2024-01-01", "2024-01-15", 15);
        int res4 = calendar.getHolidaysCount("2024-02-05", "2024-04-05", 61);

        Assertions.assertEquals(0, res1);
        Assertions.assertEquals(0, res2);
        Assertions.assertEquals(10, res3);
        Assertions.assertEquals(20, res4);
    }

    @Test
    public void Calendar_getHolidaysCount_WrongDatesException() {
        Exception exception = Assertions.assertThrows(WrongDatesException.class, () -> {
            calendar.getHolidaysCount("2024-02-09", "2024-02-05", 5);
        });

        String expectedMessage = "Дата начала отпуска позже, чем дата окончания отпуска";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void Calendar_getHolidaysCount_WrongVacationDaysException() {
        Exception exception = Assertions.assertThrows(WrongVacationDaysException.class, () -> {
            calendar.getHolidaysCount("2024-02-05", "2024-02-09", 3);
        });

        String expectedMessage = "Указанное количество дней отпуска превышает количество дней между датами";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }


}
