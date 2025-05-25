package com.academy.LibraryManagementSystem.service;

import com.academy.LibraryManagementSystem.model.Review;
import com.academy.LibraryManagementSystem.model.Transaction;

import java.sql.Timestamp;
import java.util.List;

public interface TransactionService {

    List<Transaction> findAllTransactions();

    Transaction saveTransaction(Transaction transaction);

    Transaction findByTransactionType(Transaction transactionType);

    Transaction updateTransaction(Transaction transaction);

    void deleteByTransactionDate(Timestamp dueDate);
}
