package com.example.datecalculator.service;

import com.example.datecalculator.cache.EntityCache;
import com.example.datecalculator.dto.responsedto.UserResponseDto;
import com.example.datecalculator.exception.NotFoundException;
import com.example.datecalculator.exception.BadRequestException;
import com.example.datecalculator.exception.ServerException;
import com.example.datecalculator.model.Date;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.datecalculator.model.User;
import com.example.datecalculator.dto.UserDto;
import com.example.datecalculator.repository.UserRepository;
import com.example.datecalculator.repository.DateRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.example.datecalculator.utilities.Conctants.*;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final DateRepository dateRepository;
    private final EntityCache<Long, Object> entityCache;

    @Autowired
    public UserService(UserRepository userRepository, DateRepository dateRepository,
                       EntityCache<Long, Object> entityCache) {
        this.userRepository = userRepository;
        this.dateRepository = dateRepository;
        this.entityCache = entityCache;
    }

    public User findById(Long id) {
        try {
            Long hashCode = (long) Objects.hash(id, 31 * 32);
            Object cachedData = entityCache.get(hashCode);
            if (cachedData != null) {
                return (User) cachedData;
            } else {
                Optional<User> optionalUser = userRepository.findById(id);
                if (!optionalUser.isPresent()) {
                    throw new BadRequestException(INVALID_INFO_MSG);
                }
                User user = optionalUser.get();
                entityCache.put(hashCode, user);
                return user;
            }
        } catch (Exception e) {
            throw new ServerException(SERVER_ERROR_MSG);
        }
    }

    public Optional<List<User>> getAllUsers() {
        try {
            List<User> userList = userRepository.findAll();
            if (!userList.isEmpty()) {
                userList.forEach(user -> entityCache.put(user.getId(), user));
                return Optional.of(userList);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new ServerException(SERVER_ERROR_MSG);
        }
    }

    public List<UserResponseDto> findUsersByName(String name) {
        if (name == null || name.equals(" ")) {
            throw new BadRequestException(INVALID_INFO_MSG);
        } else {
            try {
                List<UserResponseDto> response = new ArrayList<>();
                for (User user : userRepository.searchByName(name)) {
                    response.add(new UserResponseDto(user));
                }
                return response;
            } catch (Exception e) {
                throw new ServerException(SERVER_ERROR_MSG);
            }
        }
    }

    @Transactional
    public ResponseEntity<Object> addUsers(List<UserDto> users) {
        if (users == null || users.isEmpty()) {
            throw new NotFoundException(NOT_FOUND_MSG);
        }

        List<User> addedUsers = new ArrayList<>();
        List<String> errors = users.stream()
                .map(user -> {
                    try {
                        User addedUser = addUser(user);
                        if (user != null) {
                            addedUsers.add(addedUser);
                        }
                        return null;
                    } catch (Exception e) {
                        return e.getMessage();
                    }
                })
                .filter(Objects::nonNull)
                .toList();

        entityCache.clear();
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(
                    "Errors occurred during bulk creation: " + String.join("   ||||   ", errors));
        }
        return new ResponseEntity<>(addedUsers, HttpStatus.OK);
    }

    public User addUser(UserDto userDto) {
        try {
            entityCache.clear();
            User user = new User();
            user.setName(userDto.getName());
            user.setPassword(userDto.getPassword());
            user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            return userRepository.save(user);
        } catch (Exception e) {
            throw new ServerException(SERVER_ERROR_MSG);
        }
    }

    public User updateUser(Long id, String name, String password) {
        entityCache.clear();
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new NotFoundException(NOT_FOUND_MSG);
        } else {
            try {
                User user = optionalUser.get();
                user.setName(name);
                user.setPassword(password);
                user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

                userRepository.save(user);
                return user;
            } catch (Exception e) {
                throw new ServerException(SERVER_ERROR_MSG);
            }
        }

    }

    public void deleteUser(Long id) {
        entityCache.clear();
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new NotFoundException(NOT_FOUND_MSG);
        }
        try {
            User user = optionalUser.get();
            userRepository.delete(user);
        } catch (Exception e) {
            throw new ServerException(SERVER_ERROR_MSG);
        }
    }

    public List<Date> getDatesByUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new BadRequestException(INVALID_INFO_MSG);
        }
        try {
            return dateRepository.findByUserId(userId);
        } catch (Exception e) {
            throw new ServerException(SERVER_ERROR_MSG);
        }
    }
}