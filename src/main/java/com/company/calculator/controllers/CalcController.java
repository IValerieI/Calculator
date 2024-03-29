package com.company.calculator.controllers;

import com.company.calculator.dto.VacationResponse;
import com.company.calculator.service.CalcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calculacte")
public class CalcController {

    private final CalcService calcService;

    @Autowired
    public CalcController(CalcService calcService) {
        this.calcService = calcService;
    }

    @GetMapping()
    public ResponseEntity<VacationResponse> calculateVacation(
            @RequestParam(value = "salary", defaultValue = "50000") double salary,
            @RequestParam(value = "numOfVacationDays", defaultValue = "14") int numOfVacationDays
    ) {
        VacationResponse vacationResponse = calcService.calculateVacation(salary, numOfVacationDays);
        return new ResponseEntity<>(vacationResponse, HttpStatus.OK);
    }


}
