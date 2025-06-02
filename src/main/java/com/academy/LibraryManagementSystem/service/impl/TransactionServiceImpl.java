package com.academy.LibraryManagementSystem.service.impl;

import com.academy.LibraryManagementSystem.model.Book;
import com.academy.LibraryManagementSystem.model.Transaction;
import com.academy.LibraryManagementSystem.model.User;
import com.academy.LibraryManagementSystem.repository.BookRepository;
import com.academy.LibraryManagementSystem.repository.TransactionRepository;
import com.academy.LibraryManagementSystem.repository.UserRepository;
import com.academy.LibraryManagementSystem.service.TransactionService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {


    private final UserRepository userRepository;

    private final BookRepository bookRepository;

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(UserRepository userRepository, BookRepository bookRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;

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


    @Override
    public void borrowBook(Integer bookId, String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        Optional<Book> bookOpt = bookRepository.findById(bookId);

        if (userOpt.isPresent() && bookOpt.isPresent()) {
            User user = userOpt.get();
            Book book = bookOpt.get();

            if (book.getAvailableCopies() > 0) {
                book.setAvailableCopies(book.getAvailableCopies() - 1);
                bookRepository.save(book);

                Transaction transaction = Transaction.builder()
                        .book(book)
                        .user(user)
                        .transactionType("BORROW")
                        .transactionDate(Timestamp.valueOf(LocalDateTime.now()))
                        .dueDate(Timestamp.valueOf(LocalDateTime.now().plusDays(14)))
                        .status(1)
                        .build();

                transactionRepository.save(transaction);
            } else {
                throw new RuntimeException("Нет доступных копий книги.");
            }
        } else {
            throw new RuntimeException("Пользователь или книга не найдены.");
        }
    }

    @Override
    public void returnBook(Integer transactionId) {
        Optional<Transaction> transactionOpt = transactionRepository.findById(transactionId);

        if (transactionOpt.isPresent()) {
            Transaction transaction = transactionOpt.get();
            Book book = transaction.getBook();

            book.setAvailableCopies(book.getAvailableCopies() + 1);
            bookRepository.save(book);

            transaction.setStatus(0);
            transactionRepository.save(transaction);
        } else {
            throw new RuntimeException("Транзакция не найдена.");
        }
    }

    @Override
    public List<Transaction> findByUser(User user) {
        return transactionRepository.findByUser(user);
    }
    }
