package com.company.calculator.controllers;

import com.company.calculator.dto.VacationResponse;
import com.company.calculator.service.CalcService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.hibernate.validator.constraints.Range;
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
            @RequestParam(value = "avgMonthSalary") @Min(0) double avgMonthSalary,
            @RequestParam(value = "vacationDays") @Range(min = 1, max = 180) int vacationDays
    ) {
        VacationResponse vacationResponse = calcService.calcVacationPay(avgMonthSalary, vacationDays);
        return new ResponseEntity<>(vacationResponse, HttpStatus.OK);
    }


}
