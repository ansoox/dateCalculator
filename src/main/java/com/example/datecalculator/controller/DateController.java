package com.example.datecalculator.controller;

import com.example.datecalculator.dto.ResponseDto.DateListResponseDto;
import com.example.datecalculator.dto.ResponseDto.DateResponseDto;
import com.example.datecalculator.dto.ResponseDto.TagListResponseDto;
import com.example.datecalculator.model.Date;
import com.example.datecalculator.model.Tag;
import com.example.datecalculator.dto.DateDto;
import com.example.datecalculator.service.DateService;
import org.springframework.http.ResponseEntity;
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
        Date date = dateService.findById(id);
        return new DateResponseDto(date);
    }

    @PostMapping
    public DateResponseDto addDate(@RequestBody DateDto dateDto) {
        Date createdDate = dateService.addDate(dateDto);
        return new DateResponseDto(createdDate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDate(@PathVariable(name = "id") Long id) {
        boolean isDeleted = dateService.deleteDate(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<DateResponseDto> updateDate(@RequestBody DateDto dateDto, @PathVariable(name = "id") Long id) {
        Date updatedDate = dateService.updateDate(id, dateDto);
        DateResponseDto responseDate = new DateResponseDto(updatedDate);
        return responseDate != null ? ResponseEntity.ok(responseDate) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public DateResponseDto updateUser(@PathVariable Long id, @RequestBody DateDto dateDto) {
        Date updatedDate = dateService.addTagToDate(id, dateDto);
        return new DateResponseDto(updatedDate);
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
