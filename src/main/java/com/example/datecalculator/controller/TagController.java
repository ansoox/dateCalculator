package com.example.datecalculator.controller;

import com.example.datecalculator.dto.DateDto;
import com.example.datecalculator.dto.responsedto.DateListResponseDto;
import com.example.datecalculator.dto.responsedto.TagListResponseDto;
import com.example.datecalculator.dto.responsedto.TagResponseDto;
import com.example.datecalculator.model.Date;
import com.example.datecalculator.model.Tag;
import com.example.datecalculator.dto.TagDto;
import com.example.datecalculator.model.User;
import com.example.datecalculator.service.RequestCounterService;
import com.example.datecalculator.service.TagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tag")
public class TagController {
    private final TagService tagService;
    private final RequestCounterService counterService;

    public TagController(TagService tagService, RequestCounterService counterService) {
        this.tagService = tagService;
        this.counterService = counterService;
    }

    @GetMapping
    public String getAll(Model model) {
        counterService.requestIncrement();
        List<TagResponseDto> tags = new ArrayList<>();
        for (Tag tag : tagService.getAllTags()) {
            tags.add(new TagResponseDto(tag));
        }
        if (!tags.isEmpty()) {
            model.addAttribute("tags", tags);
            return "Tags";
        } else {
            model.addAttribute("tags", Collections.emptyList());
            return "Tags";
        }
    }

//    @GetMapping
//    public List<TagResponseDto> getALL() {
//        counterService.requestIncrement();
//        List<TagResponseDto> response = new ArrayList<>();
//        for (Tag tag : tagService.getAllTags()) {
//            response.add(new TagResponseDto(tag));
//        }
//        return response;
//    }

    @GetMapping("/{id}")
    public TagResponseDto getTagById(@PathVariable(name = "id") Long id) {
        counterService.requestIncrement();
        return new TagResponseDto(tagService.findById(id));
    }

    @GetMapping("/add")
    public String creatingTag(Model model) {
        model.addAttribute("tagDto", new TagDto());
        return "AddTag";
    }

    @PostMapping("/add")
    public String createTag(@ModelAttribute TagDto tagDto, Model model) {
        counterService.requestIncrement();
        tagService.addTag(tagDto);
        return "redirect:/tag";
    }

//    @PostMapping
//    public Tag addTag(@RequestBody TagDto tagDto) {
//        counterService.requestIncrement();
//        return tagService.addTag(tagDto);
//    }

    @DeleteMapping("/{id}")
    public String deleteTag(@PathVariable(name = "id") Long id) {
        counterService.requestIncrement();
        tagService.deleteTag(id);
        return "redirect:/tag";
    }

//    @DeleteMapping("/{id}")
//    public void deleteTag(@PathVariable(name = "id") Long id) {
//        counterService.requestIncrement();
//        tagService.deleteTag(id);
//    }

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
