package com.company.calculator.exceptions;

public class WrongVacationDaysException extends RuntimeException {
    private static final long id = 1;

    public WrongVacationDaysException(String message) {
        super(message);
    }
}
