package com.example.datecalculator.controller;

import com.example.datecalculator.dto.responsedto.DateListResponseDto;
import com.example.datecalculator.dto.responsedto.UserResponseDto;
import com.example.datecalculator.model.User;
import com.example.datecalculator.model.Date;
import com.example.datecalculator.dto.UserDto;
import com.example.datecalculator.service.RequestCounterService;
import com.example.datecalculator.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final RequestCounterService counterService;

    public UserController(UserService userService, RequestCounterService counterService) {
        this.userService = userService;
        this.counterService = counterService;
    }

    @GetMapping
    public String getAll(Model model) {
        counterService.requestIncrement();
        Optional<List<User>> optionalUsers = userService.getAllUsers();
        if (optionalUsers.isPresent()) {
            model.addAttribute("users", optionalUsers.get());
            return "Users";
        } else {
            model.addAttribute("users", Collections.emptyList());
            return "Users";
        }
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable(name = "id") Long id, Model model) {
        counterService.requestIncrement();
        model.addAttribute("user", new UserResponseDto(userService.findById(id)));
        return "UserInfo";
    }

    @GetMapping("/search")
    public List<UserResponseDto> searchUsersByName(@RequestParam String name) {
        counterService.requestIncrement();
        return userService.findUsersByName(name);
    }

    @GetMapping("/get-request-count")
    public String getRequestCount() {
        int totalRequestCount = counterService.getRequestCount();
        return "Requests count: " + totalRequestCount;
    }

    @GetMapping("/add")
    public String creatingUser(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "AddUser";
    }

    @PostMapping("/add")
    public String createUser(@ModelAttribute UserDto userDto, Model model) {
        counterService.requestIncrement();
        userService.addUser(userDto);
        return "redirect:/user";
    }

    @PostMapping("/bulk")
    public ResponseEntity<Object> createUsers(@RequestBody List<UserDto> users) {
        counterService.requestIncrement();
        return userService.addUsers(users);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable(name = "id") Long id, Model model) {
        counterService.requestIncrement();
        userService.deleteUser(id);
        return "redirect:/user";
    }

    @GetMapping("/{id}/update")
    public String updatingUser(@PathVariable(name = "id") Long id, Model model) {
        UserDto userDto = new UserDto();
        userDto.setId(id);
        model.addAttribute("user", userDto);
        return "UpdateUser";
    }

    @PutMapping("/{id}/update")
    public String updateUser(@ModelAttribute UserDto userDto, @PathVariable(name = "id") Long id, Model model) {
        counterService.requestIncrement();
        userService.updateUser(id, userDto.getName(), userDto.getPassword());
        return "redirect:/user";
    }

    @GetMapping("/getUserDates/{id}")
    public List<DateListResponseDto> getUserDates(@PathVariable(name = "id") Long id) {
        counterService.requestIncrement();
        List<DateListResponseDto> response = new ArrayList<>();
        for (Date date : userService.getDatesByUser(id)) {
            response.add(new DateListResponseDto(date));
        }
        return response;
    }
}
