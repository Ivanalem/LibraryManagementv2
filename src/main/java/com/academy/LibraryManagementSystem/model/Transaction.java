package com.academy.LibraryManagementSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Data
@Table(name = "transactions")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    @Column(name = "transaction_type")
    private String transactionType; // "BORROW" или "RETURN"
    @CreationTimestamp
    @Column(name = "transaction_date")
    private Timestamp transactionDate;
    @UpdateTimestamp
    @Column(name = "due_date")
    private Timestamp dueDate;

    private Integer status; // 1 - активна, 0 - завершена

}
