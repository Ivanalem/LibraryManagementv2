package com.academy.LibraryManagementSystem.controller;

import com.academy.LibraryManagementSystem.model.Transaction;
import com.academy.LibraryManagementSystem.model.User;
import com.academy.LibraryManagementSystem.service.TransactionService;
import com.academy.LibraryManagementSystem.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    private Principal principal;

    @InjectMocks
    private TransactionController transactionController;

    private Transaction testTransaction;
    private User testUser;
    private List<Transaction> testTransactions;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1);
        testUser.setUsername("testuser");

        testTransaction = new Transaction();
        testTransaction.setId(1);
        testTransaction.setUser(testUser);
        testTransaction.setTransactionType("BORROW");
        testTransaction.setTransactionDate(new Timestamp(System.currentTimeMillis()));

        testTransactions = Arrays.asList(testTransaction);
    }

    @Test
    void findAllTransactions_ShouldReturnAllTransactions() {
        when(transactionService.findAllTransactions()).thenReturn(testTransactions);

        List<Transaction> result = transactionController.findAllTransactions();

        assertEquals(testTransactions, result);
        verify(transactionService).findAllTransactions();
    }

    @Test
    void saveTransaction_ShouldSaveAndReturnTransaction() {
        when(transactionService.saveTransaction(testTransaction)).thenReturn(testTransaction);

        Transaction result = transactionController.saveTransaction(testTransaction);

        assertEquals(testTransaction, result);
        verify(transactionService).saveTransaction(testTransaction);
    }

    @Test
    void findByTransactionType_ShouldReturnTransaction() {
        when(transactionService.findByTransactionType(any(Transaction.class))).thenReturn(testTransaction);

        Transaction result = transactionController.findByTransactionType(testTransaction);

        assertEquals(testTransaction, result);
        verify(transactionService).findByTransactionType(testTransaction);
    }

    @Test
    void updateTransaction_ShouldUpdateAndReturnTransaction() {
        when(transactionService.saveTransaction(testTransaction)).thenReturn(testTransaction);

        Transaction result = transactionController.updateTransaction(testTransaction);

        assertEquals(testTransaction, result);
        verify(transactionService).saveTransaction(testTransaction);
    }

    @Test
    void deleteByTransactionDate_ShouldCallService() {
        Timestamp testDate = new Timestamp(System.currentTimeMillis());
        transactionController.deleteByTransactionDate(testDate);
        verify(transactionService).deleteByTransactionDate(testDate);
    }

    @Test
    void borrowBook_ShouldRedirectToBooks() {
        when(principal.getName()).thenReturn("testuser");

        String result = transactionController.borrowBook(1, principal);

        assertEquals("redirect:/api/v1/books", result);
        verify(transactionService).borrowBook(1, "testuser");
    }

    @Test
    void returnBook_ShouldRedirectToTransaction() {
        String result = transactionController.returnBook(1);

        assertEquals("redirect:/api/v1/transaction", result);
        verify(transactionService).returnBook(1);
    }

    @Test
    void userTransactions_ShouldReturnTransactionsView() {
        when(principal.getName()).thenReturn("testuser");
        when(userService.findByUsername("testuser")).thenReturn(java.util.Optional.of(testUser));
        when(transactionService.findByUser(testUser)).thenReturn(testTransactions);

        String result = transactionController.userTransactions(model, principal);

        assertEquals("transactions", result);
        verify(model).addAttribute("transactions", testTransactions);
        verify(userService).findByUsername("testuser");
        verify(transactionService).findByUser(testUser);
    }

    @Test
    void userTransactions_WhenUserNotFound_ShouldThrowException() {
        when(principal.getName()).thenReturn("testuser");
        when(userService.findByUsername("testuser")).thenReturn(java.util.Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            transactionController.userTransactions(model, principal);
        });

        verify(userService).findByUsername("testuser");
        verifyNoInteractions(transactionService);
    }
}