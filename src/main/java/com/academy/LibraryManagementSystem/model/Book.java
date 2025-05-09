package com.academy.LibraryManagementSystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "books")
public class Book {
    @Id
    private Integer id;
    private String title;
    private String genre;
    @Column(name = "published_year")
    private Integer publishedYear;
    @Column(name = "available_copies")
    private Integer availableCopies;
    @Column(name = "updated_at")
    private Timestamp updatedAt;
    @Column(name = "created_at")
    private Timestamp createdAt;
}
