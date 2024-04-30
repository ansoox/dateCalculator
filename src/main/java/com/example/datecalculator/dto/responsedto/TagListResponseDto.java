package com.example.datecalculator.dto.responsedto;

import com.example.datecalculator.model.Tag;

public class TagListResponseDto {
    private Long id;
    private String tagName;

    public TagListResponseDto(Tag tag) {
        this.id = tag.getId();
        this.tagName = tag.getTagName();
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
}
