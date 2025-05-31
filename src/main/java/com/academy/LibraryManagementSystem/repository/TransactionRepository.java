package com.academy.LibraryManagementSystem.repository;

import com.academy.LibraryManagementSystem.model.Book;
import com.academy.LibraryManagementSystem.model.Transaction;
import com.academy.LibraryManagementSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    Transaction findByTransactionType(Transaction transactionType);

    void deleteByTransactionDate(Timestamp dueDate);


    List<Transaction> findByUser(User user);
}
