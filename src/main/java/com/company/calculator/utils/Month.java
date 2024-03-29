package com.company.calculator.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Month {

    @JsonProperty("month")
    private String month;
    @JsonProperty("days")
    private String days;

    public String getMonth() {
        return month;
    }

    public String getDays() {
        return days;
    }
}
