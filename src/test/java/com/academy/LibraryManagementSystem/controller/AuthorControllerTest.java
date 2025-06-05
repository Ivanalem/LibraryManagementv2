package com.academy.LibraryManagementSystem.controller;

import com.academy.LibraryManagementSystem.MockServiceTestConfig;
import com.academy.LibraryManagementSystem.model.Author;
import com.academy.LibraryManagementSystem.service.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthorController.class)
@Import(MockServiceTestConfig.class)
public class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthorService authorService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testFindAllAuthors() throws Exception {
        Author author = new Author();
        author.setName("J.K. Rowling");

        when(authorService.findAllAuthors()).thenReturn(List.of(author));

        mockMvc.perform(get("/api/v1/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("J.K. Rowling"));
    }

    @Test
    public void testSaveAuthor() throws Exception {
        Author author = new Author();
        author.setName("Tolkien");

        mockMvc.perform(post("/api/v1/authors/save_author")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(author)))
                .andExpect(status().isOk())
                .andExpect(content().string("Author successfully saved"));
    }

    @Test
    public void testFindByName() throws Exception {
        Author author = new Author();
        author.setName("Orwell");

        when(authorService.findByName("Orwell")).thenReturn(author);

        mockMvc.perform(get("/api/v1/authors/Orwell"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Orwell"));
    }
}

