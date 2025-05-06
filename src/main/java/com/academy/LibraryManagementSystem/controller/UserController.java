package com.academy.LibraryManagementSystem.controller;

import lombok.AllArgsConstructor;
import com.academy.LibraryManagementSystem.model.User;
import com.academy.LibraryManagementSystem.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> findAllUsers() {

        return userService.findAllUsers();
    }

    @PostMapping("save_user")
    public String saveUser(@RequestBody User user) {
         userService.saveUser(user);
         return "User successfully saved";
    }

    @GetMapping("/{email}")
    public User findByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }

    @PutMapping("update_user")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("delete_user/{email}")
    public void deleteUser(@PathVariable String email) {
        userService.deleteUser(email);
    }
}
