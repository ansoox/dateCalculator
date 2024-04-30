package com.example.datecalculator.dto;

import java.sql.Timestamp;

public class HistoryDto {
    private Long userId;
    private Timestamp firstDate;
    private Timestamp secondDate;
    private Timestamp diffInDays;
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Timestamp getFirstDate() {
        return firstDate;
    }

    public void setFirstDate(Timestamp firstDate) {
        this.firstDate = firstDate;
    }

    public Timestamp getSecondDate() {
        return secondDate;
    }

    public void setSecondDate(Timestamp secondDate) {
        this.secondDate = secondDate;
    }

    public Timestamp getDiffInDays() {
        return diffInDays;
    }
}
