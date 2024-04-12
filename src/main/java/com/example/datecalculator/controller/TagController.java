package com.example.datecalculator.controller;

import com.example.datecalculator.dto.DateDto;
import com.example.datecalculator.dto.ResponseDto.DateListResponseDto;
import com.example.datecalculator.dto.ResponseDto.TagListResponseDto;
import com.example.datecalculator.dto.ResponseDto.TagResponseDto;
import com.example.datecalculator.dto.ResponseDto.UserListResponseDto;
import com.example.datecalculator.model.Date;
import com.example.datecalculator.model.Tag;
import com.example.datecalculator.dto.TagDto;
import com.example.datecalculator.model.User;
import com.example.datecalculator.service.DateService;
import com.example.datecalculator.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/calculate/tag")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<TagListResponseDto> getALL() {
        List<TagListResponseDto> response = new ArrayList<>();
        for (Tag tag : tagService.getAllTags()) {
            response.add(new TagListResponseDto(tag));
        }
        return response;
    }

    @GetMapping("/{id}")
    public TagResponseDto getTagById(@PathVariable(name = "id") Long id) {
        Tag tag = tagService.findById(id);
        return new TagResponseDto(tag);
    }

    @PostMapping
    public ResponseEntity<Tag> addTag(@RequestBody TagDto tagDto) {
        Tag createdTag = tagService.addTag(tagDto);
        return ResponseEntity.ok(createdTag);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable(name = "id") Long id) {
        boolean isDeleted = tagService.deleteTag(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tag> updateTag(@RequestBody TagDto tagDto, @PathVariable(name = "id") Long id) {
        Tag updatedTag = tagService.updateTag(id, tagDto.getTagName());
        return updatedTag != null ? ResponseEntity.ok(updatedTag) : ResponseEntity.notFound().build();
    }

    @GetMapping("/getTagDates/{id}")
    public List<DateListResponseDto> getTagDates(@PathVariable (name = "id") Long id) {
        List<DateListResponseDto> response = new ArrayList<>();
        for (Date date : tagService.getDatesByTag(id)) {
            response.add(new DateListResponseDto(date));
        }
        return response;
    }
}
