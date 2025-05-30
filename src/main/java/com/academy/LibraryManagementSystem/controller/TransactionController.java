package com.academy.LibraryManagementSystem.controller;

import com.academy.LibraryManagementSystem.model.Transaction;
import com.academy.LibraryManagementSystem.model.User;
import com.academy.LibraryManagementSystem.repository.UserRepository;
import com.academy.LibraryManagementSystem.service.TransactionService;
import com.academy.LibraryManagementSystem.service.UserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<Transaction> findAllTransactions() {

        return transactionService.findAllTransactions();
    }

    @PostMapping("save_transact")
    public Transaction saveTransaction(@RequestBody Transaction transaction) {

        return transactionService.saveTransaction(transaction);
    }

    @GetMapping(value = "/{transactionType}")
    public Transaction findByTransactionType(@PathVariable Transaction transactionType) {

        return transactionService.findByTransactionType(transactionType);
    }

    @PutMapping("update_transaction")
    public Transaction updateTransaction(@RequestBody Transaction transaction) {
        return transactionService.saveTransaction(transaction);
    }

    @Transactional
    @DeleteMapping("delete_dueDate/{dueDate}")
    public void deleteByTransactionDate(@PathVariable Timestamp dueDate) {
        transactionService.deleteByTransactionDate(dueDate);
    }

    @PostMapping("/borrow")
    public String borrowBook(@RequestParam("bookId") Integer bookId, Principal principal) {
        transactionService.borrowBook(bookId, principal.getName());
        return "redirect:/books/details/" + bookId;
    }

    @PostMapping("/return")
    public String returnBook(@RequestParam("transactionId") Integer transactionId) {
        transactionService.returnBook(transactionId);
        return "redirect:transactions";
    }
}


