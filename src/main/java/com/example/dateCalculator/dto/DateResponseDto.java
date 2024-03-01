package com.example.dateCalculator.dto;

public class DateResponseDto {

    private long diffInDays;

    public DateResponseDto(long diffInDays) {
        this.diffInDays = diffInDays;
    }

    public long getDiffInDays() {
        return diffInDays;
    }

    public void setDiffInDays(long diffInDays) {
        this.diffInDays = diffInDays;
    }
}
