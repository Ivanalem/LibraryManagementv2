package com.academy.LibraryManagementSystem.service;

import com.academy.LibraryManagementSystem.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {

    List<Book> findAllBooks();

    Book saveBook(Book book);

    Book findByTitle(String title);

    Book updateBook(Book book);

    void deleteByTitle(String title);
}
