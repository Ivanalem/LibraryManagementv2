package com.academy.LibraryManagementSystem.repository;

import com.academy.LibraryManagementSystem.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    Transaction findByTransactionType(Transaction transactionType);

    void deleteByTransactionDate(Timestamp dueDate);
}
