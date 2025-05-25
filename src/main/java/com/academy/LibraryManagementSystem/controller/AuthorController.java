package com.academy.LibraryManagementSystem.controller;

import com.academy.LibraryManagementSystem.model.Author;
import com.academy.LibraryManagementSystem.model.User;
import com.academy.LibraryManagementSystem.service.AuthorService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public List<Author> findAllAuthors() {

        return authorService.findAllAuthors();
    }

    @PostMapping("save_author")
    public String saveUser(@RequestBody Author author) {
        authorService.saveAuthor(author);
        return "Author successfully saved";
    }

    @GetMapping("/{name}")
    public Author findByName(@PathVariable String name) {
        return authorService.findByName(name);
    }

    @PutMapping("update_author")
    public Author updateAuthor(@RequestBody Author author) {
        return authorService.updateAuthor(author);
    }

    @Transactional
    @DeleteMapping("delete/{name}")
    public void deleteByName(@PathVariable String name) {
        authorService.deleteByName(name);
    }
}
