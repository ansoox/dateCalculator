package com.example.datecalculator.controller;

import com.example.datecalculator.dto.responsedto.DateListResponseDto;
import com.example.datecalculator.dto.responsedto.DateResponseDto;
import com.example.datecalculator.dto.responsedto.TagListResponseDto;
import com.example.datecalculator.model.Date;
import com.example.datecalculator.model.Tag;
import com.example.datecalculator.dto.DateDto;
import com.example.datecalculator.service.DateService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/calculate/date")
public class DateController {
    private final DateService dateService;

    public DateController(DateService dateService) {
        this.dateService = dateService;
    }

    @GetMapping
    public List<DateListResponseDto> getALL() {
        List<DateListResponseDto> response = new ArrayList<>();
        for (Date date : dateService.getAllDates()) {
            response.add(new DateListResponseDto(date));
        }
        return response;
    }

    @GetMapping("/{id}")
    public DateResponseDto getDateById(@PathVariable(name = "id") Long id) {
        return new DateResponseDto(dateService.findById(id));
    }

    @PostMapping
    public DateResponseDto addDate(@RequestBody DateDto dateDto) {
        return new DateResponseDto(dateService.addDate(dateDto));
    }

    @DeleteMapping("/{id}")
    public void deleteDate(@PathVariable(name = "id") Long id) {
        dateService.deleteDate(id);
    }

    @PutMapping("/{id}")
    public DateResponseDto updateDate(@RequestBody DateDto dateDto, @PathVariable(name = "id") Long id) {
        Date updatedDate = dateService.updateDate(id, dateDto);
        return new DateResponseDto(updatedDate);
    }

    @PatchMapping("/{id}")
    public DateResponseDto updateUser(@PathVariable Long id, @RequestBody DateDto dateDto) {
        return new DateResponseDto(dateService.addTagToDate(id, dateDto));
    }

    @GetMapping("/getDateTags/{id}")
    public List<TagListResponseDto> getDateTags(@PathVariable(name = "id") Long id) {
        List<TagListResponseDto> response = new ArrayList<>();
        for (Tag tag : dateService.getTagsByDate(id)) {
            response.add(new TagListResponseDto(tag));
        }
        return response;
    }
}
