package com.company.calculator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VacationResponse {

    private double vacationPay;
    public void setVacationPay(double vacationPay) {
        this.vacationPay = vacationPay;
    }

}
