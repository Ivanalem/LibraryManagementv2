package com.academy.LibraryManagementSystem.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;


@Controller
@RequestMapping("/api/v1")
public class AppController {


    @GetMapping("/index")
    public String homePage(Model model, Principal principal) {
        model.addAttribute("user", principal.getName());
        return "index";
    }
}
