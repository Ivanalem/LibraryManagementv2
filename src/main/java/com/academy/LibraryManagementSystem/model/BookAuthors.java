package com.academy.LibraryManagementSystem.model;


import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.awt.print.Book;

@Data
@Table(name = "bookauthors")
@Entity
public class BookAuthors {

    private Book book;
    private Author author;
}
