package com.academy.LibraryManagementSystem.controller;

import com.academy.LibraryManagementSystem.model.Book;
import com.academy.LibraryManagementSystem.service.BookService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> findAllBooks() {

        return bookService.findAllBooks();
    }

    @PostMapping("save_book")
    public String saveBook(@RequestBody Book book) {
        bookService.saveBook(book);
        return "Book successfully saved";
    }

    @GetMapping("/{title}")
    public Book findByTitle(@PathVariable String title) {

        return bookService.findByTitle(title);
    }

    @PutMapping("update_book")
    public Book updateBook(@RequestBody Book book) {
        return bookService.updateBook(book);
    }

    @Transactional
    @DeleteMapping("delete/{title}")
    public void deleteByTitle(@PathVariable String title) {
        bookService.deleteByTitle(title);
    }
}
