package com.company.calculator.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WrongVacationDaysException.class)
    public ResponseEntity<ErrorObject> handleWrongVacationDaysException(WrongVacationDaysException ex) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());
        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WrongDatesException.class)
    public ResponseEntity<ErrorObject> handleWrongDatesException(WrongDatesException ex) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());
        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }

}
