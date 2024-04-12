package com.example.datecalculator.service;

import com.example.datecalculator.dto.ResponseDto.DateListResponseDto;
import com.example.datecalculator.dto.ResponseDto.TagListResponseDto;
import com.example.datecalculator.dto.ResponseDto.UserResponseDto;
import com.example.datecalculator.model.Date;
import org.springframework.stereotype.Service;

import com.example.datecalculator.model.User;
import com.example.datecalculator.model.History;
import com.example.datecalculator.dto.UserDto;
import com.example.datecalculator.repository.UserRepository;
import com.example.datecalculator.repository.DateRepository;
import com.example.datecalculator.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final DateRepository dateRepository;
    private final HistoryRepository historyRepository;

    @Autowired
    public UserService(UserRepository userRepository, DateRepository dateRepository, HistoryRepository historyRepository) {
        this.userRepository = userRepository;
        this.dateRepository = dateRepository;
        this.historyRepository = historyRepository;
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<UserResponseDto> findUsersByName(String name) {
        List<UserResponseDto> response = new ArrayList<>();
        for (User user : userRepository.searchByName(name)) {
            response.add(new UserResponseDto(user));
        }
        return response;
    }

    public User addUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return userRepository.save(user);
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
        if (user != null) {
            userRepository.delete(user);
            return true;
        }
        return false;
    }

    public List<Date> getDatesByUser(Long userId) {
        return dateRepository.findByUserId(userId);
    }

    //public List<History> getHistoriesByUser(Long userId) {
    //    return historyRepository.findByUserId(userId);
    //}
}