package com.academy.LibraryManagementSystem.service.impl;

import com.academy.LibraryManagementSystem.model.Book;
import com.academy.LibraryManagementSystem.repository.BookRepository;
import com.academy.LibraryManagementSystem.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Override
    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteByTitle(String title) {
        bookRepository.deleteByTitle(title);
    }
}
