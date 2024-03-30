package com.company.calculator.exceptions;

public class WrongVacationDaysException extends RuntimeException {

    public WrongVacationDaysException(String message) {
        super(message);
    }
}
