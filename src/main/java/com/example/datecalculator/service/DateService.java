package com.example.datecalculator.service;

import com.example.datecalculator.dto.ResponseDto.TagListResponseDto;
import org.springframework.stereotype.Service;

import com.example.datecalculator.model.Date;
import com.example.datecalculator.model.Tag;
import com.example.datecalculator.model.User;
import com.example.datecalculator.dto.DateDto;
import com.example.datecalculator.repository.DateRepository;
import com.example.datecalculator.repository.TagRepository;
import com.example.datecalculator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;

import java.util.List;

@Service
public class DateService {
    private final DateRepository dateRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;

    @Autowired
    public DateService(DateRepository dateRepository, TagRepository tagRepository, UserRepository userRepository) {
        this.dateRepository = dateRepository;
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
    }

    public Date addDate(DateDto dateDto) {
        User user = userRepository.findById(dateDto.getUserId()).orElseThrow(() -> new RuntimeException("Tag not found"));
        Date date = new Date();
        date.setDate(dateDto.getDate());
        date.setUser(user);
        date.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        date.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return dateRepository.save(date);
    }

    public Date addTagToDate(Long dateId, DateDto dateDto) {
        Date date = dateRepository.findById(dateId).orElseThrow(() -> new RuntimeException("Date not found"));
        Tag tag = tagRepository.findById(dateDto.getTagId()).orElseThrow(() -> new RuntimeException("Tag not found"));
        if (date.getTags().contains(tag)) {
            throw new RuntimeException("Tag already exists for this date");
        }
        date.getTags().add(tag);
        //tag.getDates().add(date);
        date.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        tag.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        tagRepository.save(tag);
        return dateRepository.save(date);
    }

    public Date updateDate(Long id, DateDto dateDto) {
        Date date = dateRepository.findById(id).orElse(null);
        if (date != null) {
            date.setDate(dateDto.getDate());
            date.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            for (Tag tag : date.getTags()) {
                List<Date> dates = tag.getDates();
                dates.remove(date);
                dates.add(date);
                tagRepository.save(tag);
            }

            dateRepository.save(date);
        }
        return date;
    }

    public boolean deleteDate(Long id) {
        Date date = dateRepository.findById(id).orElse(null);
        if (date != null) {
            dateRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Date> getAllDates() {
        return dateRepository.findAll();
    }

    public Date findById(Long id) {
        return dateRepository.findById(id).orElse(null);
    }

    public List<Tag> getTagsByDate(Long dateId) {
        return tagRepository.findByDateId(dateId);
    }
}
