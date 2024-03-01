package com.example.dateCalculator.dto;

import java.time.LocalDate;

public class DateRequestDto {

    private String firstDate;
    private String secondDate;

    public DateRequestDto(String firstDate, String secondDate) {
        this.firstDate = firstDate;
        this.secondDate = secondDate;
    }

    public String getFirstDate() {
        return firstDate;
    }

    public void setFirstDate(String firstDate) {
        this.firstDate = firstDate;
    }

    public String getSecondDate() {
        return secondDate;
    }

    public void setSecondDate(String secondDate) {
        this.secondDate = secondDate;
    }
}
