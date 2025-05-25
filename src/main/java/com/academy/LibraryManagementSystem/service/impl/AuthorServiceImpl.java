package com.academy.LibraryManagementSystem.service.impl;

import com.academy.LibraryManagementSystem.model.Author;
import com.academy.LibraryManagementSystem.repository.AuthorRepository;
import com.academy.LibraryManagementSystem.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Author findByName(String name) {
        return authorRepository.findByName(name);
    }

    @Override
    public Author updateAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public void deleteByName(String name) {
        authorRepository.deleteByName(name);
    }
}
