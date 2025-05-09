package com.academy.LibraryManagementSystem.model;


import jakarta.persistence.Table;
import lombok.Data;

import java.awt.print.Book;

@Data
@Table(name = "bookauthors")
public class BookAuthors {
    private Book book;
    private Author author;
}
