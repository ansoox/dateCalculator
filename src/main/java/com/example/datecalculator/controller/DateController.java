package com.example.datecalculator.controller;

import com.example.datecalculator.dto.DateAddDto;
import com.example.datecalculator.dto.responsedto.DateResponseDto;
import com.example.datecalculator.dto.responsedto.TagListResponseDto;
import com.example.datecalculator.model.Date;
import com.example.datecalculator.model.Tag;
import com.example.datecalculator.dto.DateDto;
import com.example.datecalculator.service.DateService;
import com.example.datecalculator.service.RequestCounterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/date")
public class DateController {
    private final DateService dateService;
    private final RequestCounterService counterService;

    private static final String DATES_ATTRIBUTE = "dates";

    public DateController(DateService dateService, RequestCounterService counterService) {
        this.dateService = dateService;
        this.counterService = counterService;
    }

    @GetMapping
    public String getAll(Model model) {
        counterService.requestIncrement();
        List<DateResponseDto> dates = new ArrayList<>();
        for (Date date : dateService.getAllDates()) {
            dates.add(new DateResponseDto(date));
        }
        if (!dates.isEmpty()) {
            model.addAttribute(DATES_ATTRIBUTE, dates);
            return "Dates";
        } else {
            model.addAttribute(DATES_ATTRIBUTE, Collections.emptyList());
            return "Dates";
        }
    }

    @GetMapping("/{id}")
    public DateResponseDto getDateById(@PathVariable(name = "id") Long id) {
        counterService.requestIncrement();
        return new DateResponseDto(dateService.findById(id));
    }

    @GetMapping("/add")
    public String creatingDate(Model model) {
        model.addAttribute("dateAddDto", new DateAddDto());
        return "AddDate";
    }

    @PostMapping("/add")
    public String createDate(@ModelAttribute DateAddDto dateAddDto, BindingResult result, Model model) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(dateAddDto.getAddDate(), formatter);

        Timestamp timestamp = Timestamp.valueOf(dateTime);

        dateAddDto.setDate(timestamp);

        dateService.addDate(dateAddDto);
        return "redirect:/date";
    }

    @DeleteMapping("/{id}")
    public String deleteDate(@PathVariable(name = "id") Long id) {
        counterService.requestIncrement();
        dateService.deleteDate(id);
        return "redirect:/date";
    }

    @PutMapping("/{id}")
    public DateResponseDto updateDate(@RequestBody DateDto dateDto, @PathVariable(name = "id") Long id) {
        counterService.requestIncrement();
        Date updatedDate = dateService.updateDate(id, dateDto);
        return new DateResponseDto(updatedDate);
    }

    @GetMapping("/{tagId}/addDateToTag")
    public String addingDateToTag(@PathVariable(name = "tagId") Long tagId, Model model) {
        List<DateResponseDto> dates = new ArrayList<>();
        for (Date date : dateService.getAllDates()) {
            dates.add(new DateResponseDto(date));
        }
        if (!dates.isEmpty()) {
            model.addAttribute("dates", dates);
        } else {
            model.addAttribute("dates", Collections.emptyList());
        }
        return "AddDateToTag";
    }

    @PatchMapping("/{tagId}/addDateToTag")
    public String addDateToTag(@PathVariable Long tagId, @RequestParam Long dateId) {
        counterService.requestIncrement();
        DateDto dateDto = new DateDto();
        dateDto.setTagId(tagId);
        dateService.addTagToDate(dateId, dateDto);
        return "redirect:/tag";
    }

    @GetMapping("/getDateTags/{id}")
    public List<TagListResponseDto> getDateTags(@PathVariable(name = "id") Long id) {
        counterService.requestIncrement();
        List<TagListResponseDto> response = new ArrayList<>();
        for (Tag tag : dateService.getTagsByDate(id)) {
            response.add(new TagListResponseDto(tag));
        }
        return response;
    }
}
