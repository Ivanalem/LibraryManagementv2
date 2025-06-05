package com.academy.LibraryManagementSystem.controller;

import com.academy.LibraryManagementSystem.model.Book;
import com.academy.LibraryManagementSystem.model.Review;
import com.academy.LibraryManagementSystem.model.User;
import com.academy.LibraryManagementSystem.service.BookService;
import com.academy.LibraryManagementSystem.service.ReviewService;
import com.academy.LibraryManagementSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final BookService bookService;
    private final UserService userService;
    @Autowired
    public ReviewController(ReviewService reviewService, BookService bookService, UserService userService) {
        this.reviewService = reviewService;
        this.bookService = bookService;
        this.userService = userService;
    }

    @GetMapping
    public List<Review> findAllReviews() {
        return reviewService.findAllReviews();
    }

    @PostMapping("/save_review")
    public Review saveReview(@RequestBody Review review) {
        return reviewService.saveReview(review);
    }

    @PutMapping("update_review")
    public Review updateReview(@RequestBody Review review) {
        return reviewService.saveReview(review);
    }

    @Transactional
    @DeleteMapping("/delete_comment/{comment}")
    public void deleteByComment(@PathVariable Integer reviewId) {
        reviewService.deleteReview(reviewId);
    }
    //the form of leaving a comment
    @PostMapping("/books/{bookId}/reviews")
    public String submitReview(@PathVariable Integer bookId,
                               @ModelAttribute Review review,
                               Principal principal,
                               RedirectAttributes redirectAttributes) {

        if (principal == null) {
            redirectAttributes.addFlashAttribute("error", "Вы должны войти в систему.");
            return "redirect:/api/v1/login";
        }

        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));;
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Пользователь не найден.");
            return "redirect:/api/v1/login";
        }

        Optional<Review> existingReview = reviewService.getUserReviewForBook(bookId, user.getId());
        if (existingReview.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Вы уже оставили отзыв.");
            return "redirect:/api/v1/books";
        }

        Book book = bookService.findBookById(bookId);
        review.setBook(book);
        review.setUser(user);
        review.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        reviewService.saveReview(review);
        return "redirect:/api/v1/books/";
    }
    // Show the review form
    @GetMapping("/books/{bookId}/reviews")
    public String showReviewForm(@PathVariable Integer bookId, Model model) {
        model.addAttribute("review", new Review()); // напрямую сущность Review
        model.addAttribute("bookId", bookId);
        return "review-form";
    }
}
