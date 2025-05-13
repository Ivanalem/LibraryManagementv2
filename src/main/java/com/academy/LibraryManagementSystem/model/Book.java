package com.academy.LibraryManagementSystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "books")
@AllArgsConstructor
@NoArgsConstructor
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
