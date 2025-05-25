package com.academy.LibraryManagementSystem.service;

import com.academy.LibraryManagementSystem.model.Author;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuthorService {

    List<Author> findAllAuthors();

    Author saveAuthor(Author author);

    Author findByName(String name);

    Author updateAuthor(Author author);

    void deleteByName(String name);
}
