package com.example.datecalculator.controller;

import com.example.datecalculator.dto.responsedto.DateListResponseDto;
import com.example.datecalculator.dto.responsedto.TagListResponseDto;
import com.example.datecalculator.dto.responsedto.TagResponseDto;
import com.example.datecalculator.model.Date;
import com.example.datecalculator.model.Tag;
import com.example.datecalculator.dto.TagDto;
import com.example.datecalculator.service.RequestCounterService;
import com.example.datecalculator.service.TagService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/calculate/tag")
public class TagController {
    private final TagService tagService;
    private final RequestCounterService counterService;

    public TagController(TagService tagService, RequestCounterService counterService) {
        this.tagService = tagService;
        this.counterService = counterService;
    }

    @GetMapping
    public List<TagListResponseDto> getALL() {
        counterService.requestIncrement();
        List<TagListResponseDto> response = new ArrayList<>();
        for (Tag tag : tagService.getAllTags()) {
            response.add(new TagListResponseDto(tag));
        }
        return response;
    }

    @GetMapping("/{id}")
    public TagResponseDto getTagById(@PathVariable(name = "id") Long id) {
        counterService.requestIncrement();
        return new TagResponseDto(tagService.findById(id));
    }

    @PostMapping
    public Tag addTag(@RequestBody TagDto tagDto) {
        counterService.requestIncrement();
        return tagService.addTag(tagDto);
    }

    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable(name = "id") Long id) {
        counterService.requestIncrement();
        tagService.deleteTag(id);
    }

    @PutMapping("/{id}")
    public Tag updateTag(@RequestBody TagDto tagDto, @PathVariable(name = "id") Long id) {
        counterService.requestIncrement();
        return tagService.updateTag(id, tagDto.getTagName());
    }

    @GetMapping("/getTagDates/{id}")
    public List<DateListResponseDto> getTagDates(@PathVariable(name = "id") Long id) {
        counterService.requestIncrement();
        List<DateListResponseDto> response = new ArrayList<>();
        for (Date date : tagService.getDatesByTag(id)) {
            response.add(new DateListResponseDto(date));
        }
        return response;
    }
}
