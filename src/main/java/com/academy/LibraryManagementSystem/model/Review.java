package com.academy.LibraryManagementSystem.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Book book;
    private User user;
    private Integer rating;
    private String comment;
    @Column(name = "created_at")
    private Timestamp createdAt;
}
