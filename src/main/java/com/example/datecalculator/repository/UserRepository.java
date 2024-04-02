package com.example.datecalculator.repository;

import com.example.datecalculator.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User searchByName(String name);

    User searchById(Long id);
}