package com.academy.LibraryManagementSystem.controller;

import com.academy.LibraryManagementSystem.model.Transaction;
import com.academy.LibraryManagementSystem.model.User;
import com.academy.LibraryManagementSystem.service.TransactionService;
import com.academy.LibraryManagementSystem.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping("/api/v1")
public class TransactionController {

    private final TransactionService transactionService;
    private final UserService userService;

    public TransactionController(TransactionService transactionService, UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }

    @GetMapping("/all_transactions")
    public List<Transaction> findAllTransactions() {

        return transactionService.findAllTransactions();
    }

    @PostMapping("save_transact")
    public Transaction saveTransaction(@RequestBody Transaction transaction) {

        return transactionService.saveTransaction(transaction);
    }

    @GetMapping(value = "/type/{transactionType}")
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
        return "redirect:/api/v1/books";
    }

    @PostMapping("/return")
    public String returnBook(@RequestParam("transactionId") Integer transactionId) {
        transactionService.returnBook(transactionId);
        return "redirect:/api/v1/transaction";
    }

    @GetMapping("/transaction")
    public String userTransactions(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow();
        List<Transaction> transactions = transactionService.findByUser(user);
        model.addAttribute("transactions", transactions);
        return "transactions";
    }
}


