package com.company.calculator.service;

import com.company.calculator.dto.VacationResponse;
import org.springframework.stereotype.Service;

@Service
public class CalcService {

    public VacationResponse calculateVacation(double salary, int numOfVacationDays) {
        VacationResponse vacationResponse = new VacationResponse();
        vacationResponse.setResult("res");
        return vacationResponse;
    }




}
