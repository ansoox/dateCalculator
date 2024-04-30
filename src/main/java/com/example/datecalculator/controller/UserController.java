package com.example.datecalculator.controller;

import com.example.datecalculator.dto.responsedto.DateListResponseDto;
import com.example.datecalculator.dto.responsedto.UserResponseDto;
import com.example.datecalculator.model.User;
import com.example.datecalculator.model.Date;
import com.example.datecalculator.dto.UserDto;
import com.example.datecalculator.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/calculate/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAll() {
        Optional<List<User>> optionalUsers = userService.getAllUsers();
        if (optionalUsers.isPresent()) {
            List<UserResponseDto> response = optionalUsers.get().stream()
                    .map(UserResponseDto::new)
                    .toList();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable(name = "id") Long id) {
            return new UserResponseDto(userService.findById(id));
    }

    @GetMapping("/search")
    public List<UserResponseDto> searchUsersByName(@RequestParam String name) {
        return userService.findUsersByName(name);
    }

    @PostMapping
    public User createUser(@RequestBody UserDto userDto) {
        return userService.addUser(userDto);
    }

    @PostMapping("/bulk")
    public ResponseEntity<Object> createUsers(@RequestBody List<UserDto> users) {
        return userService.addUsers(users);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable(name = "id") Long id) {
        userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@RequestBody UserDto userDto, @PathVariable(name = "id") Long id) {
        return userService.updateUser(id, userDto.getName(), userDto.getPassword());
    }

    @GetMapping("/getUserDates/{id}")
    public List<DateListResponseDto> getUserDates(@PathVariable(name = "id") Long id) {
        List<DateListResponseDto> response = new ArrayList<>();
        for (Date date : userService.getDatesByUser(id)) {
            response.add(new DateListResponseDto(date));
        }
        return response;
    }
}
