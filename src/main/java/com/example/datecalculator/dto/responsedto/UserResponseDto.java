package com.example.datecalculator.dto.responsedto;

import com.example.datecalculator.model.Date;
import com.example.datecalculator.model.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class UserResponseDto {
    private Long id;
    private String name;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    //private List<History> historyList = new ArrayList<>();
    private List<DateResponseDto> dates = new ArrayList<>();

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
        //this.historyList = user.getHistoryList();
        for (Date date : user.getDates()) {
            this.dates.add(new DateResponseDto(date));
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

//    public List<History> getHistoryList() {
//        return historyList;
//    }

//    public void setHistoryList(List<History> historyList) {
//        this.historyList = historyList;
//    }

    public List<DateResponseDto> getDates() {
        return dates;
    }

    public void setDates(List<DateResponseDto> dates) {
        this.dates = dates;
    }
}
