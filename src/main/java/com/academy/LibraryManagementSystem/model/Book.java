package com.academy.LibraryManagementSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "books")
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String genre;
    @Column(name = "published_year")
    private Integer publishedYear;
    @Column(name = "available_copies")
    private Integer availableCopies;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;
    @Column(name="description")
    private String description;
    @ManyToMany(mappedBy = "books")
    private List<Author> authors = new ArrayList<>();
}
