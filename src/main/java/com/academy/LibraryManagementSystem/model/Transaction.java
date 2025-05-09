package com.academy.LibraryManagementSystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private User user;
    private Book book;
    @Column(name = "transaction_type")
    private String transactionType;
    @Column(name = "transaction_date")
    private Timestamp transactionDate;
    @Column(name = "due_date")
    private Timestamp dueDate;
    private Integer status;
}
