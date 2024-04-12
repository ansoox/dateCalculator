package com.example.datecalculator.repository;

import com.example.datecalculator.model.Date;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DateRepository extends JpaRepository<Date, Long> {
    @Query("SELECT d FROM Date d JOIN d.tags t WHERE t.id = :tagId")
    List<Date> findByTagId(@Param("tagId") Long tagId);

    List<Date> findByUserId(Long userId);
}