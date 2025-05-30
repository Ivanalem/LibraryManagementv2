package com.academy.LibraryManagementSystem.controller;

import com.academy.LibraryManagementSystem.model.Book;
import com.academy.LibraryManagementSystem.model.User;
import com.academy.LibraryManagementSystem.repository.UserRepository;
import com.academy.LibraryManagementSystem.service.BookService;
import com.academy.LibraryManagementSystem.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/api/v1/admin")
//@PreAuthorize("hasRole('ADMIN')")
public class AdminController {


    private final BookService bookService;
    private final UserRepository userRepository;
    private final UserService userService;

    public AdminController(BookService bookService, UserRepository userRepository, UserService userService) {
        this.bookService = bookService;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/adminDashboard")
    public String adminDashboard() {
        return "adminDashboard";
    }


    @GetMapping("/save_book")
    public String addBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "add-book";
    }

    @PostMapping("/save_book")
    public String saveBook(@ModelAttribute Book book) {
        bookService.saveBook(book);
        return "redirect:/admin/books";
    }

    @GetMapping("/books")
    public String findAllBooks(Model model) {
        model.addAttribute("books", bookService.findAllBooks());
        return "books";
    }

    @GetMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable Integer id) {
        bookService.deleteById(id);
        return "redirect:/admin/books";
    }

    @GetMapping("/users")
    public String userList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users";
    }

    @PostMapping("/users/{id}/change-role")
    public String changeUserRole(@PathVariable Integer id, @RequestParam User.Role newRole) {
        userService.changeUserRole(id, newRole);
        return "redirect:/admin/users";
    }

    @Bean
    public CommandLineRunner createAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setEmail("admin@library.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(Set.of(User.Role.ROLE_ADMIN));
                admin.setStatus("ACTIVE");
                userRepository.save(admin);
            }
        };
    }
}
