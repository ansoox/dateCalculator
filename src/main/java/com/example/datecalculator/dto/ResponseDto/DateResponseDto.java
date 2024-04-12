package com.example.datecalculator.dto.ResponseDto;

import com.example.datecalculator.model.Date;
import com.example.datecalculator.model.User;
import com.example.datecalculator.model.Tag;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DateResponseDto {
    private Long id;
    private Timestamp date;
    private Timestamp updatedAt;
    private Timestamp createdAt;
    private UserListResponseDto user;
    private List<TagListResponseDto> tags = new ArrayList<>();

    public DateResponseDto(Date date) {
        this.id = date.getId();
        this.date = date.getDate();
        this.updatedAt = date.getUpdatedAt();
        this.createdAt = date.getCreatedAt();
        this.user = new UserListResponseDto(date.getUser());
        for (Tag tag : date.getTags()) {
            this.tags.add(new TagListResponseDto(tag));
        }
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

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public UserListResponseDto getUser() {
        return user;
    }

    public void setUser(UserListResponseDto user) {
        this.user = user;
    }

    public List<TagListResponseDto> getTags() {
        return tags;
    }

    public void setTags(List<TagListResponseDto> tags) {
        this.tags = tags;
    }
}
