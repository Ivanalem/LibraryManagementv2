package com.academy.LibraryManagementSystem.config;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AppConfiguration {

    @GetMapping("/index")
    public String homePage() {
        return "index";
    }
}
