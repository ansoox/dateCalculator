package com.example.datecalculator.service;

import com.example.datecalculator.dto.TagDto;
import com.example.datecalculator.model.History;
import com.example.datecalculator.model.Tag;
import com.example.datecalculator.model.Date;
import com.example.datecalculator.repository.DateRepository;
import com.example.datecalculator.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.List;

@Service
public class TagService {
    private final TagRepository tagRepository;
    private final DateRepository dateRepository;

    @Autowired
    public TagService(TagRepository tagRepository, DateRepository dateRepository) {
        this.tagRepository = tagRepository;
        this.dateRepository = dateRepository;
    }

    public Tag addTag(TagDto tagDto) {
       // if (tagRepository.searchByName(tagDto.getTagName()) != null) {
       //     return null;
       // }
        Tag tag = new Tag();
        tag.setName(tagDto.getTagName()); //если имя есть уже , то нельзя
        tag.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        tag.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return tagRepository.save(tag);
    }

    public Tag findById(Long id) {
        return tagRepository.findById(id).orElse(null);
    }

    public Tag updateTag(Long id, String name) {
        Tag tag = tagRepository.findById(id).orElse(null);
        if (tag != null) {
            tag.setName(name);
            tag.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            for (Date date : tag.getDates()) {
                List<Tag> tags = date.getTags();
                tags.remove(tag);
                tags.add(tag);
                dateRepository.save(date);
            }

            tagRepository.save(tag);
        }
        return tag;
    }

    public boolean deleteTag(Long id) {
        Tag tag = tagRepository.findById(id).orElse(null);
        if (tag != null) {
            tagRepository.delete(tag);
            for (Date date : tag.getDates()) {
                List<Tag> tags = date.getTags();
                tags.remove(tag);
                dateRepository.save(date);
            }
            return true;
        }
        return false;
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public List<Date> getDatesByTag(Long tagId) {
        return dateRepository.findByTagId(tagId);
    }

}
