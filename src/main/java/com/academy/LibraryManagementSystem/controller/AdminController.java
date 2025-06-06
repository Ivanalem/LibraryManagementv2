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

    public AdminController(BookService bookService, UserService userService, TransactionService transactionService) {
        this.bookService = bookService;
        this.userService = userService;
        this.transactionService = transactionService;
    }


    //search for all transactions and display
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

    @PostMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable Integer id) {
        bookService.deleteById(id);
        return "redirect:/api/v1/books";
    }

    @GetMapping("/users")
    public String userList(Model model) {
        List<User> users = userService.findAllUsers();
        List<String> roles = List.of("USER", "ADMIN");

        model.addAttribute("users", users);
        model.addAttribute("roles", roles);
        return "admin-users";

    }

    @PostMapping("/users/{email}/toggle-status")
    public String toggleUserStatus(@PathVariable String email) {
        User user = userService.findByEmail(email);
        if ("ACTIVE".equals(user.getStatus())) {
            user.setStatus("INACTIVE");
        } else {
            user.setStatus("ACTIVE");
        }

        userService.saveUser(user);
        return "redirect:/api/v1/admin/users";
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
        return "redirect:/api/v1/admin/users";
    }
}
