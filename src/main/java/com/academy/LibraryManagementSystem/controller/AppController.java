package com.academy.LibraryManagementSystem.controller;

import com.academy.LibraryManagementSystem.model.Book;
import com.academy.LibraryManagementSystem.model.Review;
import com.academy.LibraryManagementSystem.model.User;
import com.academy.LibraryManagementSystem.service.BookService;
import com.academy.LibraryManagementSystem.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/v1")
public class AppController {

    private final UserService userService;
    private final BookService bookService;

    public AppController(UserService userService, BookService bookService) {
        this.userService = userService;
        this.bookService = bookService;
    }

    @GetMapping("/index")
    public String homePage(Model model, Principal principal) {
        model.addAttribute("user", principal.getName());
        return "index";
    }

    @GetMapping("/search")
    public String searchBooks(@RequestParam("query")String query, Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());

        List<Book> foundBooks = bookService.findAllBooks();
        Map<Integer, Double> ratings = new HashMap<>();
        Map<Integer, List<Review>> reviews = new HashMap<>();


        model.addAttribute("books", foundBooks);
        model.addAttribute("ratings", ratings);
        model.addAttribute("reviews", reviews);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("role", user.getRole());
        model.addAttribute("searchQuery", query);

        return "index"; // отобразим ту же самую страницу index.html
    }


}
