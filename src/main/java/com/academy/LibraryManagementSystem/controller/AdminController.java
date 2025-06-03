package com.academy.LibraryManagementSystem.controller;

import com.academy.LibraryManagementSystem.model.Book;
import com.academy.LibraryManagementSystem.model.Transaction;
import com.academy.LibraryManagementSystem.model.User;
import com.academy.LibraryManagementSystem.repository.UserRepository;
import com.academy.LibraryManagementSystem.service.BookService;
import com.academy.LibraryManagementSystem.service.TransactionService;
import com.academy.LibraryManagementSystem.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/api/v1/admin")
public class AdminController {


    private final BookService bookService;
    private final UserService userService;
    private final TransactionService transactionService;

    public AdminController(BookService bookService, UserRepository userRepository, UserService userService, TransactionService transactionService) {
        this.bookService = bookService;
        this.userService = userService;
        this.transactionService = transactionService;
    }


    @GetMapping("/transaction")
    public String adminTransaction(Model model) {
        List<Transaction> transactions = transactionService.findAllTransactions();
        model.addAttribute("transactions", transactions);
        return "transactions";
    }



    @GetMapping("/save_book")
    public String addBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "admin-add-book";
    }

    @PostMapping("/save_book")
    public String saveBook(@ModelAttribute Book book) {
        bookService.saveBook(book);
        return "redirect:/api/v1/books";
    }

    @GetMapping("/books")
    public String findAllBooks(Model model) {
        model.addAttribute("books", bookService.findAllBooks());
        return "books";
    }

    @GetMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable Integer id) {
        bookService.deleteById(id);
        return "redirect:/admin-users";
    }

    @GetMapping("/users")
    public String userList(Model model) {
            List<User> users = userService.findAllUsers();
            List<String> roles = List.of("USER", "ADMIN"); // или откуда берёте

            model.addAttribute("users", users);
            model.addAttribute("roles", roles);
            return "admin-users";

    }

    @PostMapping("/users/{id}/change-role")
    public String changeUserRole(@PathVariable Integer id, @RequestParam User.Role newRole) {
        userService.changeUserRole(id, newRole);
        return "redirect:/admin-users";
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
    @Transactional
    @PostMapping("/delete_user/{email}")
    public String deleteUser(@PathVariable String email) {
        userService.deleteByEmail(email);
        return "redirect:/admin-users";
    }
}
