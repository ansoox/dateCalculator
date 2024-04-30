package com.example.datecalculator.service;

import com.example.datecalculator.exception.BadRequestException;
import com.example.datecalculator.exception.NotFoundException;
import com.example.datecalculator.exception.ServerException;
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
import java.util.Optional;

import static com.example.datecalculator.utilities.Conctants.*;

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
        try {
            User user = userRepository.findById(dateDto.getUserId()).orElseThrow(() -> new RuntimeException("Tag not found"));
            Date date = new Date();
            date.setDate(dateDto.getDate());
            date.setUser(user);
            date.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            date.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            return dateRepository.save(date);
        } catch (Exception e) {
            throw new ServerException(SERVER_ERROR_MSG);
        }
    }

    public Date addTagToDate(Long dateId, DateDto dateDto) {
        Optional<Date> optionalDate = dateRepository.findById(dateId);
        Optional<Tag> optionalTag = tagRepository.findById(dateDto.getTagId());
        if (optionalDate.isEmpty() || optionalTag.isEmpty()) {
            throw new BadRequestException(INVALID_INFO_MSG);
        }
        Date date = optionalDate.get();
        Tag tag = optionalTag.get();

        if (date.getTags().contains(tag)) {
            throw new BadRequestException(INVALID_INFO_MSG);
        }
        try {
            date.getTags().add(tag);
            date.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            tag.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            tagRepository.save(tag);
            return dateRepository.save(date);
        } catch (Exception e) {
            throw new ServerException(SERVER_ERROR_MSG);
        }
    }

    public Date updateDate(Long id, DateDto dateDto) {
        Optional<Date> optionalDate = dateRepository.findById(id);
        if (optionalDate.isEmpty()) {
            throw new NotFoundException(NOT_FOUND_MSG);
        } else {
            try {
                Date date = optionalDate.get();
                date.setDate(dateDto.getDate());
                date.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                for (Tag tag : date.getTags()) {
                    List<Date> dates = tag.getDates();
                    dates.remove(date);
                    dates.add(date);
                    tagRepository.save(tag);
                }
                dateRepository.save(date);
                return date;
            } catch (Exception e) {
                throw new ServerException(SERVER_ERROR_MSG);
            }
        }
    }

    public void deleteDate(Long id) {
        Optional<Date> optionalDate = dateRepository.findById(id);
        if (optionalDate.isEmpty()) {
            throw new NotFoundException(NOT_FOUND_MSG);
        } else {
            try {
                Date date = optionalDate.get();
                dateRepository.delete(date);
            } catch (Exception e) {
                throw new ServerException(SERVER_ERROR_MSG);
            }
        }
    }

    public List<Date> getAllDates() {
        try {
            return dateRepository.findAll();
        } catch (Exception e) {
            throw new ServerException(SERVER_ERROR_MSG);
        }
    }

    public Date findById(Long id) {
        Optional<Date> foundDate = dateRepository.findById(id);
        if (foundDate.isPresent()) {
            return foundDate.get();
        } else {
            throw new BadRequestException(INVALID_INFO_MSG);
        }
    }

    public List<Tag> getTagsByDate(Long dateId) {
        try {
            return tagRepository.findByDateId(dateId);
        } catch (Exception e) {
            throw new ServerException(SERVER_ERROR_MSG);
        }
    }
}
