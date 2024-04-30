package com.example.datecalculator.dto.responsedto;

import com.example.datecalculator.model.User;

public class UserListResponseDto {
    private Long id;
    private String name;

    public UserListResponseDto() {
    }

    public UserListResponseDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
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
}
