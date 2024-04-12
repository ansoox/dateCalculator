package com.example.datecalculator.dto;

import com.example.datecalculator.model.Tag;

import java.sql.Timestamp;
import java.util.List;

public class DateDto {
    private Long tagId;
    private Long userId;
    private Timestamp date;

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
