package com.academy.LibraryManagementSystem.service;

import com.academy.LibraryManagementSystem.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BookService {

    List<Book> searchBooks(String query, String genre);
    List<String> findAllGenres();

    List<Book> findAllBooks();

    Book saveBook(Book book);

    Optional<Book> findById(Integer id);

    Book updateBook(Book book);

    void deleteById(Integer id);
}
