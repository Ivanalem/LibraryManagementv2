package com.academy.LibraryManagementSystem.service.impl;

import com.academy.LibraryManagementSystem.model.Transaction;
import com.academy.LibraryManagementSystem.repository.TransactionRepository;
import com.academy.LibraryManagementSystem.service.TransactionService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {

        this.transactionRepository = transactionRepository;
    }
    @Override
    public List<Transaction> findAllTransactions() {

        return transactionRepository.findAll();
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {

        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction findByTransactionType(Transaction transactionType) {

        return transactionRepository.findByTransactionType(transactionType);
    }

    @Override
    public Transaction updateTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public void deleteByTransactionDate(Timestamp dueDate) {
        transactionRepository.deleteByTransactionDate(dueDate);
    }
}
