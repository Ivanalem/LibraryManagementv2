package com.academy.LibraryManagementSystem.service.impl;

import com.academy.LibraryManagementSystem.model.Author;
import com.academy.LibraryManagementSystem.model.Book;
import com.academy.LibraryManagementSystem.repository.AuthorRepository;
import com.academy.LibraryManagementSystem.repository.BookRepository;
import com.academy.LibraryManagementSystem.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }


    @Override
    public List<Book> searchBooks(String query, String genre) {
        if ((query == null || query.isEmpty()) && (genre == null || genre.isEmpty())) {
            return bookRepository.findAll();
        }

        if (query != null && !query.isEmpty() && genre != null && !genre.isEmpty()) {
            return bookRepository.findByTitleContainingIgnoreCaseAndGenreIgnoreCase(query, genre);
        } else if (query != null && !query.isEmpty()) {
            return bookRepository.findByTitleContainingIgnoreCase(query);
        } else {
            return bookRepository.findByGenreIgnoreCase(genre);
        }
    }

    @Override
    public List<String> findAllGenres() {
        return bookRepository.findDistinctGenres();
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
    public Optional<Book> findById(Integer id) {
        return bookRepository.findById(id);
    }


    @Override
    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteById(Integer id) {
        bookRepository.deleteById(id);
    }

    @Override
    public void saveBookWithAuthors(Book book, List<Author> authors) {
        authorRepository.saveAll(authors);
        bookRepository.save(book);

    }
    public List<Book> findByGenre(String genre) {
        return bookRepository.findByGenre(genre);
    }
}
