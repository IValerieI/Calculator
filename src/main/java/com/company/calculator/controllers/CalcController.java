package com.company.calculator.controllers;

import com.company.calculator.dto.VacationResponse;
import com.company.calculator.service.CalcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calculate")
public class CalcController {

    private final CalcService calcService;

    @Autowired
    public CalcController(CalcService calcService) {
        this.calcService = calcService;
    }

    @GetMapping()
    public ResponseEntity<VacationResponse> calculateVacation(
            @RequestParam(value = "avgMonthSalary", defaultValue = "100000") double avgMonthSalary,
            @RequestParam(value = "vacationDays", defaultValue = "14") int vacationDays
    ) {
        VacationResponse vacationResponse = calcService.calculateVacation(avgMonthSalary, vacationDays);
        return new ResponseEntity<>(vacationResponse, HttpStatus.OK);
    }


}
