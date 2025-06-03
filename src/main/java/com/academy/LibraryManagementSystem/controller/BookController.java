package com.academy.LibraryManagementSystem.controller;

import com.academy.LibraryManagementSystem.model.Author;
import com.academy.LibraryManagementSystem.model.Book;
import com.academy.LibraryManagementSystem.model.Review;
import com.academy.LibraryManagementSystem.model.User;
import com.academy.LibraryManagementSystem.repository.BookRepository;
import com.academy.LibraryManagementSystem.service.BookService;
import com.academy.LibraryManagementSystem.service.ReviewService;
import com.academy.LibraryManagementSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1")
public class BookController {

    private final BookService bookService;
    private final UserService userService;
    private final ReviewService reviewService;

    @Autowired
    public BookController(BookService bookService, UserService userService, ReviewService reviewService, BookRepository bookRepository) {
        this.bookService = bookService;
        this.userService = userService;
        this.reviewService = reviewService;
    }

    @PostMapping("/save_book")
    @Transactional
    public String saveBook(@RequestParam String title,
                           @RequestParam String description,
                           @RequestParam Integer publishedYear,
                           @RequestParam String genre,
                           @RequestParam Integer availableCopies,
                           @RequestParam List<String> authorNames,
                           @RequestParam(required = false) List<String> authorBios) {

        Book book = new Book();
        book.setTitle(title);
        book.setDescription(description);
        book.setPublishedYear(publishedYear);
        book.setGenre(genre);
        book.setAvailableCopies(availableCopies);

        List<Author> authors = new ArrayList<>();
        for (int i = 0; i < authorNames.size(); i++) {
            Author author = new Author();
            author.setName(authorNames.get(i));

            if (authorBios != null && i < authorBios.size()) {
                author.setBiography(authorBios.get(i));
            }

            // Инициализация списка книг вручную
            List<Book> authoredBooks = new ArrayList<>();
            authoredBooks.add(book);
            author.setBooks(authoredBooks);

            authors.add(author);
        }


        book.setAuthors(authors); // связь

        bookService.saveBookWithAuthors(book, authors);

        return "redirect:/api/v1/books";
    }

    @GetMapping("/save_book")
    public String saveForm(Book book, Model model) {
        bookService.saveBook(book);
        model.addAttribute("books", new Book());
        return "books";
    }

    @GetMapping("/books/{id}")
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

    @GetMapping("/book/{id}")
    public String bookDetails(@PathVariable Integer id, Model model, Principal principal) {
        Optional<Book> optionalBook = bookService.findById(id);

        Book book = optionalBook.get();
        model.addAttribute("book", book);
        model.addAttribute("reviews", reviewService.findByBookId(id));

        if (principal != null) {
            User user = userService.findByEmail(principal.getName());
            if (user != null) {
                Optional<Review> userReview = reviewService.getUserReviewForBook(id, user.getId());
                userReview.ifPresent(r -> model.addAttribute("userReview", r));
            }
        }

        return "book-details";
    }


    @GetMapping("/books")
    public String viewAllBooks(Model model) {
        List<Book> books = bookService.findAllBooks(); // метод сервиса, получающий все книги
        model.addAttribute("books", books);
        return "books";
    }

}
