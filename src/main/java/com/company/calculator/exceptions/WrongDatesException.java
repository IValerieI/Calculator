package com.company.calculator.exceptions;

public class WrongDatesException extends RuntimeException {
    private static final long id = 2;

    public WrongDatesException(String message) {
        super(message);
    }
}
