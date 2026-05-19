package com.academy.LibraryManagementSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "authors")
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String biography;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @ManyToMany
    @JoinTable(
            name = "bookauthors", // Имя таблицы для связи
            joinColumns = @JoinColumn(name = "author_id"), // Внешний ключ для автора
            inverseJoinColumns = @JoinColumn(name = "book_id") // Внешний ключ для книги
    )
    private List<Book> books = new ArrayList<>();
}
