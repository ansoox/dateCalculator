package com.example.datecalculator.dto.ResponseDto;

import com.example.datecalculator.model.Date;

import java.sql.Timestamp;

public class DateListResponseDto {
    private Long id;
    private Timestamp date;

    public DateListResponseDto(Date date){
        this.id = date.getId();
        this.date = date.getDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
