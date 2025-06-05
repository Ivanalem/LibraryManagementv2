package com.academy.LibraryManagementSystem.controller;

import com.academy.LibraryManagementSystem.model.Book;
import com.academy.LibraryManagementSystem.model.Review;
import com.academy.LibraryManagementSystem.model.User;
import com.academy.LibraryManagementSystem.service.BookService;
import com.academy.LibraryManagementSystem.service.ReviewService;
import com.academy.LibraryManagementSystem.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewControllerTest {

    @Mock
    private ReviewService reviewService;

    @Mock
    private BookService bookService;

    @Mock
    private UserService userService;

    @Mock
    private Principal principal;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private ReviewController reviewController;

    private User testUser;
    private Book testBook;
    private Review testReview;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1);
        testUser.setUsername("testuser");

        testBook = new Book();
        testBook.setId(1);
        testBook.setTitle("Test Book");

        testReview = new Review();
        testReview.setId(1);
        testReview.setComment("Great book!");
        testReview.setBook(testBook);
        testReview.setUser(testUser);
        testReview.setCreatedAt(new Timestamp(System.currentTimeMillis()));
    }

    @Test
    void submitReview_WhenUserNotLoggedIn_ShouldRedirectToLogin() {
        String result = reviewController.submitReview(1, testReview, null, redirectAttributes);

        assertEquals("redirect:/api/v1/login", result);
        verify(redirectAttributes).addFlashAttribute("error", "Вы должны войти в систему.");
        verifyNoInteractions(userService, bookService, reviewService);
    }

    @Test
    void submitReview_WhenUserNotFound_ShouldRedirectToLogin() {
        when(principal.getName()).thenReturn("testuser");
        when(userService.findByUsername("testuser")).thenReturn(Optional.empty());

        String result = reviewController.submitReview(1, testReview, principal, redirectAttributes);

        assertEquals("redirect:/api/v1/login", result);
        verify(redirectAttributes).addFlashAttribute("error", "Пользователь не найден.");
        verify(userService).findByUsername("testuser");
        verifyNoInteractions(bookService, reviewService);
    }

    @Test
    void submitReview_WhenReviewExists_ShouldRedirectWithError() {
        when(principal.getName()).thenReturn("testuser");
        when(userService.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(reviewService.getUserReviewForBook(1, 1)).thenReturn(Optional.of(testReview));

        String result = reviewController.submitReview(1, testReview, principal, redirectAttributes);

        assertEquals("redirect:/api/v1/books", result);
        verify(redirectAttributes).addFlashAttribute("error", "Вы уже оставили отзыв.");
        verify(reviewService).getUserReviewForBook(1, 1);
        verifyNoInteractions(bookService);
    }

    @Test
    void submitReview_WhenValidReview_ShouldSaveAndRedirect() {
        when(principal.getName()).thenReturn("testuser");
        when(userService.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(reviewService.getUserReviewForBook(1, 1)).thenReturn(Optional.empty());
        when(bookService.findBookById(1)).thenReturn(testBook);

        String result = reviewController.submitReview(1, testReview, principal, redirectAttributes);

        assertEquals("redirect:/api/v1/books/", result);
        verify(reviewService).saveReview(testReview);
        assertNotNull(testReview.getCreatedAt());
        assertEquals(testBook, testReview.getBook());
        assertEquals(testUser, testReview.getUser());
    }

    @Test
    void showReviewForm_ShouldReturnReviewFormView() {
        String result = reviewController.showReviewForm(1, model);

        assertEquals("review-form", result);
        verify(model).addAttribute(eq("review"), any(Review.class));
        verify(model).addAttribute("bookId", 1);
    }

    @Test
    void deleteByComment_ShouldCallService() {
        reviewController.deleteByComment(1);
        verify(reviewService).deleteReview(1);
    }

    @Test
    void saveReview_ShouldCallService() {
        when(reviewService.saveReview(testReview)).thenReturn(testReview);

        Review result = reviewController.saveReview(testReview);

        assertEquals(testReview, result);
        verify(reviewService).saveReview(testReview);
    }

    @Test
    void updateReview_ShouldCallService() {
        when(reviewService.saveReview(testReview)).thenReturn(testReview);

        Review result = reviewController.updateReview(testReview);

        assertEquals(testReview, result);
        verify(reviewService).saveReview(testReview);
    }

    @Test
    void findAllReviews_ShouldCallService() {
        reviewController.findAllReviews();
        verify(reviewService).findAllReviews();
    }
}