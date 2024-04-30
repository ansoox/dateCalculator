package com.example.datecalculator.repository;

import com.example.datecalculator.model.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query("SELECT t FROM Tag t JOIN t.dates d WHERE d.id = :dateId")
    List<Tag> findByDateId(@Param("dateId") Long dateId);

    //@Query("SELECT t FROM Tag t WHERE t.TagName=:name")
    Tag searchByTagName(String tagName);
}
