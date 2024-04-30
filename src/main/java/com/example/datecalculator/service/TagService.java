package com.example.datecalculator.service;

import com.example.datecalculator.dto.TagDto;
import com.example.datecalculator.exception.BadRequestException;
import com.example.datecalculator.exception.NotFoundException;
import com.example.datecalculator.exception.ServerException;
import com.example.datecalculator.model.Tag;
import com.example.datecalculator.model.Date;
import com.example.datecalculator.repository.DateRepository;
import com.example.datecalculator.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

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
        if (tagRepository.searchByTagName(tagDto.getTagName()) != null) {
            throw new ServerException("Some error on server occurred");
        }
        try {
            Tag tag = new Tag();
            tag.setName(tagDto.getTagName());
            tag.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            tag.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            tagRepository.save(tag);
            return tag;
        } catch (Exception e) {
            throw new ServerException("Some error on server occurred");
        }
    }

    public Tag findById(Long id) {
        if (tagRepository.findById(id).isPresent()) {
            return tagRepository.findById(id).get();
        } else {
            throw new BadRequestException("Invalid info provided");
        }
    }

    public Tag updateTag(Long id, String name) {
        Optional<Tag> optionalTag = tagRepository.findById(id);
        if (!optionalTag.isPresent()) {
            throw new NotFoundException("Required resources are not found");
        } else {
            try {
                Tag tag = optionalTag.get();
                tag.setName(name);
                tag.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

                for (Date date : tag.getDates()) {
                    List<Tag> tags = date.getTags();
                    tags.remove(tag);
                    //tags.removeIf(t -> t.getId().equals(tag.getId()));
                    tags.add(tag);
                    dateRepository.save(date);
                }
                tagRepository.save(tag);
                return tag;
            } catch (Exception e) {
                throw new ServerException("Some error on server occurred");
            }
        }
    }

    public void deleteTag(Long id) {
        Optional<Tag> optionalTag = tagRepository.findById(id);
        if (!optionalTag.isPresent()) {
            throw new NotFoundException("Required resources are not found");
        } else {
            try {
                Tag tag = optionalTag.get();
                for (Date date : tag.getDates()) {
                    date.getTags().remove(tag);
                    dateRepository.save(date);
                }
                tagRepository.delete(tag);
            } catch (Exception e) {
                throw new ServerException("Some error on server occurred");
            }
        }
    }

    public List<Tag> getAllTags() {
        try {
            return tagRepository.findAll();
        } catch (Exception e) {
            throw new ServerException("Some error on server occurred");
        }
    }

    public List<Date> getDatesByTag(Long tagId) {
        try {
            return dateRepository.findByTagId(tagId);
        } catch (Exception e) {
            throw new ServerException("Some error on server occurred");
        }
    }
}
