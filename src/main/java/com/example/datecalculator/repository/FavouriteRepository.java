package com.example.datecalculator.repository;

import com.example.datecalculator.model.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
    Favourite searchById(Long id);
}