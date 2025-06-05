package com.academy.LibraryManagementSystem.controller;

import com.academy.LibraryManagementSystem.model.Author;
import com.academy.LibraryManagementSystem.model.Book;
import com.academy.LibraryManagementSystem.model.Review;
import com.academy.LibraryManagementSystem.model.User;
import com.academy.LibraryManagementSystem.service.BookService;
import com.academy.LibraryManagementSystem.service.ReviewService;
import com.academy.LibraryManagementSystem.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookControllerTest {

    private BookController controller;
    private BookService bookService;
    private UserService userService;
    private ReviewService reviewService;
    private Model model;

    @BeforeEach
    void setUp() {
        bookService = mock(BookService.class);
        userService = mock(UserService.class);
        reviewService = mock(ReviewService.class);
        controller = new BookController(bookService, userService, reviewService, null);
        model = mock(Model.class);
    }

    @Test
    void shouldSaveBookWithAuthors() {
        String title = "Test Book";
        String description = "Test Description";
        Integer year = 2024;
        String genre = "Fiction";
        Integer copies = 5;
        List<String> authorNames = List.of("Author 1", "Author 2");
        List<String> authorBios = List.of("Bio 1", "Bio 2");

        String result = controller.saveBook(title, description, year, genre, copies, authorNames, authorBios);

        assertEquals("redirect:/api/v1/books", result);
        verify(bookService).saveBookWithAuthors(any(Book.class), anyList());
    }

    @Test
    void shouldRenderSaveForm() {
        Book book = new Book();
        String view = controller.saveForm(book, model);
        assertEquals("books", view);
        verify(bookService).saveBook(book);
        verify(model).addAttribute(eq("books"), any(Book.class));
    }

    @Test
    void shouldReturnBookById() {
        Book book = new Book();
        when(bookService.findById(1)).thenReturn(Optional.of(book));

        Optional<Book> result = controller.findById(1);
        assertTrue(result.isPresent());
        assertEquals(book, result.get());
    }

    @Test
    void shouldUpdateBook() {
        Book book = new Book();
        when(bookService.updateBook(book)).thenReturn(book);

        Book result = controller.updateBook(book);
        assertEquals(book, result);
    }

    @Test
    void shouldDeleteBookById() {
        controller.deleteById(5);
        verify(bookService).deleteById(5);
    }

    @Test
    void shouldSearchBooks() {
        List<Book> books = List.of(new Book());
        when(bookService.searchByTitleOrAuthor("test")).thenReturn(books);

        String view = controller.searchBooks("test", model);
        assertEquals("books", view);
        verify(model).addAttribute("books", books);
        verify(model).addAttribute("query", "test");
    }

    @Test
    void shouldViewBookDetailsWithUserReview() {
        Book book = new Book();
        Review review = new Review();
        User user = new User();
        user.setId(1);
        Principal principal = () -> "user@example.com";

        when(bookService.findById(10)).thenReturn(Optional.of(book));
        when(reviewService.findByBookId(10)).thenReturn(List.of(review));
        when(userService.findByEmail("user@example.com")).thenReturn(user);
        when(reviewService.getUserReviewForBook(10, 1)).thenReturn(Optional.of(review));

        String view = controller.bookDetails(10, model, principal);
        assertEquals("book-details", view);

        verify(model).addAttribute("book", book);
        verify(model).addAttribute("reviews", List.of(review));
        verify(model).addAttribute("userReview", review);
    }

    @Test
    void shouldViewAllBooks() {
        List<Book> books = List.of(new Book());
        when(bookService.findAllBooks()).thenReturn(books);

        String view = controller.viewAllBooks(model);
        assertEquals("books", view);
        verify(model).addAttribute("books", books);
    }

    @Test
    void shouldGetBooksByGenre() {
        List<Book> books = List.of(new Book());
        when(bookService.findByGenre("Drama")).thenReturn(books);

        String view = controller.getBooksByGenre("Drama", model);
        assertEquals("book-list", view);
        verify(model).addAttribute("books", books);
        verify(model).addAttribute("selectedGenre", "Drama");
    }
}
