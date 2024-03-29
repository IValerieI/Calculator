package com.company.calculator.service;

import com.company.calculator.dto.VacationResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CalcServiceTest {

    @InjectMocks
    private CalcService calcService;

    @Test
    public void CalcService_calcVacationPay_ReturnVacationPay() {
        VacationResponse res1 = calcService.calcVacationPay(0, 14);
        VacationResponse res2 = calcService.calcVacationPay(120000, 14);
        VacationResponse res3 = calcService.calcVacationPay(100000, 28);

        Assertions.assertEquals(0, res1.getVacationPay());
        Assertions.assertEquals(57337.88, res2.getVacationPay());
        Assertions.assertEquals(95563.14, res3.getVacationPay());
    }

    @Test
    public void CalcService_calcVacationPayDates_ReturnVacationPay() throws JsonProcessingException {
        VacationResponse res1 = calcService.calcVacationPayDates(120000, 61,
                "2024-02-05", "2024-04-05");

        Assertions.assertEquals(167918.090, res1.getVacationPay());
    }



}
