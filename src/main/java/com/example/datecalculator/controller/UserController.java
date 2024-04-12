package com.example.datecalculator.controller;

import com.example.datecalculator.dto.ResponseDto.DateListResponseDto;
import com.example.datecalculator.dto.ResponseDto.TagListResponseDto;
import com.example.datecalculator.dto.ResponseDto.UserListResponseDto;
import com.example.datecalculator.dto.ResponseDto.UserResponseDto;
import com.example.datecalculator.model.History;
import com.example.datecalculator.model.User;
import com.example.datecalculator.model.Date;
import com.example.datecalculator.dto.UserDto;
import com.example.datecalculator.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/calculate/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserListResponseDto> getALL() {
        List<UserListResponseDto> response = new ArrayList<>();
        for (User user : userService.getAllUsers()) {
            response.add(new UserListResponseDto(user));
        }
        return response;
    }

    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable(name = "id") Long id) {
        User user = userService.findById(id);
        return new UserResponseDto(user);
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserResponseDto>> searchUsersByName(@RequestParam String name) {
        List<UserResponseDto> users = userService.findUsersByName(name);
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
        User createdUser = userService.addUser(userDto);
        return ResponseEntity.ok(createdUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "id") Long id) {
        boolean isDeleted = userService.deleteUser(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestBody UserDto userDto, @PathVariable(name = "id") Long id) {
        User updatedUser = userService.updateUser(id, userDto.getName(), userDto.getPassword());
        return updatedUser != null ? ResponseEntity.ok(updatedUser) : ResponseEntity.notFound().build();
    }

    @GetMapping("/getUserDates/{id}")
    public List<DateListResponseDto> getUserDates(@PathVariable(name = "id") Long id) {
        List<DateListResponseDto> response = new ArrayList<>();
        for (Date date : userService.getDatesByUser(id)) {
            response.add(new DateListResponseDto(date));
        }
        return response;
    }

//    @GetMapping("/getUserHistories/{id}")
//    public ResponseEntity<List<History>> getUserHistories(@PathVariable (name = "id") Long id) {
//        List<History> histories = userService.getHistoriesByUser(id);
//        return histories != null ? ResponseEntity.ok(histories) : ResponseEntity.notFound().build();
//    }
}
