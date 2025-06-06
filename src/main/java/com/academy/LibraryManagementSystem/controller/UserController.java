package com.academy.LibraryManagementSystem.controller;

import com.academy.LibraryManagementSystem.model.User;
import com.academy.LibraryManagementSystem.service.UserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> findAllUser() {

        return userService.findAllUsers();
    }

    @PostMapping("/save_user")
    public String saveUser(@RequestBody User user) {
        userService.saveUser(user);
        return "User successfully saved";
    }

    @GetMapping("/{email}")
    public User findByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }

    @PutMapping("/update_user")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @Transactional
    @PostMapping("/delete_user/{email}")
    public void deleteUser(@PathVariable String email) {
        userService.deleteByEmail(email);
    }
}
