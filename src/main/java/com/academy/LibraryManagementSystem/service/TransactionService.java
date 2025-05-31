package com.academy.LibraryManagementSystem.service;

import com.academy.LibraryManagementSystem.model.Book;
import com.academy.LibraryManagementSystem.model.Review;
import com.academy.LibraryManagementSystem.model.Transaction;
import com.academy.LibraryManagementSystem.model.User;

import java.sql.Timestamp;
import java.util.List;

public interface TransactionService {

    List<Transaction> findAllTransactions();

    Transaction saveTransaction(Transaction transaction);

    Transaction findByTransactionType(Transaction transactionType);

    Transaction updateTransaction(Transaction transaction);

    void deleteByTransactionDate(Timestamp dueDate);

    void borrowBook(Integer bookId, String username);
    void returnBook(Integer transactionId);
}
