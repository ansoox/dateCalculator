package com.example.datecalculator.service;

import org.springframework.stereotype.Service;


import com.example.datecalculator.model.User;
import com.example.datecalculator.model.History;
import com.example.datecalculator.repository.UserRepository;
import com.example.datecalculator.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final HistoryRepository historyRepository;

    @Autowired
    public UserService(UserRepository userRepository, HistoryRepository historyRepository) {
        this.userRepository = userRepository;
        this.historyRepository = historyRepository;
    }

    //при создании юзера создается история
    public User addUser(User user) {
        user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return userRepository.save(user);
    }

    public User findByName(String name) {
        return userRepository.searchByName(name);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User updateUser(Long id, String name, String password) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setName(name);
            user.setPassword(password);
            user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            userRepository.save(user);
        }
        return user;
    }

    public boolean deleteUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        List<History> historyList = historyRepository.findByUserId(id);
        if (user != null) {
            historyList.clear();
            userRepository.delete(user);
            return true;
        }
        return false;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}