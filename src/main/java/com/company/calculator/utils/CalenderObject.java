package com.company.calculator.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CalenderObject {

    @JsonProperty("year")
    private String year;
    @JsonProperty("months")
    private List<Month> months;

    public String getYear() {
        return year;
    }

    public List<Month> getMonths() {
        return months;
    }

}
