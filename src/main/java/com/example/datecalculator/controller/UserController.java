package com.example.datecalculator.controller;

import com.example.datecalculator.dto.responsedto.DateListResponseDto;
import com.example.datecalculator.dto.responsedto.UserListResponseDto;
import com.example.datecalculator.dto.responsedto.UserResponseDto;
import com.example.datecalculator.model.User;
import com.example.datecalculator.model.Date;
import com.example.datecalculator.dto.UserDto;
import com.example.datecalculator.service.RequestCounterService;
import com.example.datecalculator.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

//    @GetMapping
//    public ResponseEntity<List<UserListResponseDto>> getAll() {
//        counterService.requestIncrement();
//        Optional<List<User>> optionalUsers = userService.getAllUsers();
//        if (optionalUsers.isPresent()) {
//            List<UserListResponseDto> response = optionalUsers.get().stream()
//                    .map(UserListResponseDto::new)
//                    .toList();
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable(name = "id") Long id, Model model) {
        counterService.requestIncrement();
        model.addAttribute("user", new UserResponseDto(userService.findById(id)));
        return "UserInfo";
    }

//    @GetMapping("/{id}")
//    public UserResponseDto getUserById(@PathVariable(name = "id") Long id) {
//        counterService.requestIncrement();
//        return new UserResponseDto(userService.findById(id));
//    }

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


//    @PostMapping
//    public User createUser(@RequestBody UserDto userDto) {
//        counterService.requestIncrement();
//        return userService.addUser(userDto);
//    }

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

//    @DeleteMapping("/{id}")
//    public void deleteUser(@PathVariable(name = "id") Long id) {
//        counterService.requestIncrement();
//        userService.deleteUser(id);
//    }

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

//    @PutMapping("/{id}")
//    public User updateUser(@RequestBody UserDto userDto, @PathVariable(name = "id") Long id) {
//        counterService.requestIncrement();
//        return userService.updateUser(id, userDto.getName(), userDto.getPassword());
//    }

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
