package com.example.datecalculator.dto.ResponseDto;

import com.example.datecalculator.model.Date;
import com.example.datecalculator.model.Tag;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TagResponseDto {
    private Long id;
    private String tagName;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private List<DateListResponseDto> dates = new ArrayList<>();

    public TagResponseDto(Tag tag) {
        this.id = tag.getId();
        this.tagName = tag.getTagName();
        this.createdAt = tag.getCreatedAt();
        this.updatedAt = tag.getUpdatedAt();
        for (Date date : tag.getDates()) {
            this.dates.add(new DateListResponseDto(date));
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
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

    public List<DateListResponseDto> getDates() {
        return dates;
    }

    public void setDates(List<DateListResponseDto> dates) {
        this.dates = dates;
    }
}
