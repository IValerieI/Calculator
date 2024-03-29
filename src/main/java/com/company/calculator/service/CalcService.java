package com.company.calculator.service;

import com.company.calculator.dto.VacationResponse;
import org.springframework.stereotype.Service;

@Service
public class CalcService {

    public VacationResponse calculateVacation(double avgMonthSalary, int vacationDays) {
        // 29.3 - Среднее количество календарных дней в полностью отработанных месяцах
        double vacationPay = Math.round((avgMonthSalary / 29.3) * vacationDays * 100) / 100.0;

        VacationResponse vacationResponse = new VacationResponse();
        vacationResponse.setVacationPay(vacationPay);
        return vacationResponse;
    }




}
