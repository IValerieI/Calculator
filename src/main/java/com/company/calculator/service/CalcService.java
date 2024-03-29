package com.company.calculator.service;

import com.company.calculator.dto.VacationResponse;
import com.company.calculator.utils.Calendar;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalcService {

    private final Calendar calendar;

    @Autowired
    public CalcService(Calendar calendar) {
        this.calendar = calendar;
    }

    // 29.3 - Среднее количество календарных дней в полностью отработанных месяцах

    public VacationResponse calcVacationPay(double avgMonthSalary, int vacationDays) {
        double vacationPay = Math.round((avgMonthSalary / 29.3) * vacationDays * 100) / 100.0;

        VacationResponse vacationResponse = new VacationResponse();
        vacationResponse.setVacationPay(vacationPay);
        return vacationResponse;
    }

    public VacationResponse calcVacationPayDates(double avgMonthSalary, int vacationDays, String start, String end) throws JsonProcessingException {
        int holidays = calendar.getHolidaysCount(start, end, vacationDays);
        System.out.println(avgMonthSalary / 29.3);
        System.out.println(vacationDays - holidays);
        double vacationPay = Math.round((avgMonthSalary / 29.3) * (vacationDays - holidays) * 100) / 100.0;

        VacationResponse vacationResponse = new VacationResponse();
        vacationResponse.setVacationPay(vacationPay);
        return vacationResponse;
    }




}
