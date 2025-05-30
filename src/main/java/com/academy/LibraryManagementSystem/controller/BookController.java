package com.academy.LibraryManagementSystem.controller;

import com.academy.LibraryManagementSystem.model.Book;
import com.academy.LibraryManagementSystem.model.Review;
import com.academy.LibraryManagementSystem.model.User;
import com.academy.LibraryManagementSystem.service.BookService;
import com.academy.LibraryManagementSystem.service.ReviewService;
import com.academy.LibraryManagementSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1")
public class BookController {

    private final BookService bookService;
    private final UserService userService;
    private final ReviewService reviewService;
    @Autowired
    public BookController(BookService bookService, UserService userService, ReviewService reviewService) {
        this.bookService = bookService;
        this.userService = userService;
        this.reviewService = reviewService;
    }

    @GetMapping("/books")
    public List<Book> findAllBooks() {

        return bookService.findAllBooks();
    }

    @PostMapping("/save_book")
    public String saveBook(@RequestBody Book book) {
        bookService.saveBook(book);
        return "Book successfully saved";
    }

    @GetMapping("/{id}")
    public Optional<Book> findById(@PathVariable Integer id) {

        return bookService.findById(id);
    }

    @PutMapping("/update_book")
    public Book updateBook(@RequestBody Book book) {
        return bookService.updateBook(book);
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable Integer id) {
        bookService.deleteById(id);
    }

    @GetMapping("/search")
    public String searchBooks(@RequestParam(required = false) String query,
                              @RequestParam(required = false) String genre,
                              Model model) {

        List<Book> books = bookService.searchBooks(query, genre);
        model.addAttribute("books", books);
        model.addAttribute("genres", bookService.findAllGenres());
        return "index";
    }
    @GetMapping("/books/{id}")
    public String bookDetails(@PathVariable Integer id, Model model, Principal principal) {
        Book book = bookService.findAllBooks().get(id);
        model.addAttribute("book", book);
        model.addAttribute("reviews", reviewService.findByBookId(id));

        if (principal != null) {
            User user = userService.findByEmail(principal.getName());
            Optional<Review> userReview = reviewService.getUserReviewForBook(id, user.getId());
            userReview.ifPresent(r -> model.addAttribute("userReview", r));
        }

        return "book-detail";
    }

}
