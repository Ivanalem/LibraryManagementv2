package com.academy.LibraryManagementSystem.controller;

import com.academy.LibraryManagementSystem.model.Book;
import com.academy.LibraryManagementSystem.model.Review;
import com.academy.LibraryManagementSystem.model.User;
import com.academy.LibraryManagementSystem.service.BookService;
import com.academy.LibraryManagementSystem.service.ReviewService;
import com.academy.LibraryManagementSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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

    @PostMapping("save_review")
    public Review saveReview(@RequestBody Review review) {
        return reviewService.saveReview(review);
    }

    @PutMapping("update_review")
    public Review updateReview(@RequestBody Review review) {
        return reviewService.saveReview(review);
    }

    @Transactional
    @DeleteMapping("delete_comment/{comment}")
    public void deleteByComment(@PathVariable Integer reviewId) {
        reviewService.deleteReview(reviewId);
    }

    @PostMapping("/books/{bookId}/reviews")
    public String submitReview(@PathVariable Integer bookId,
                               @RequestParam int rating,
                               @RequestParam String comment,
                               Principal principal,
                               RedirectAttributes redirectAttributes) {

        User user = userService.findByEmail(principal.getName());
        Optional<Review> existingReview = reviewService.getUserReviewForBook(bookId, user.getId());

        if (existingReview.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Вы уже оставили отзыв.");
            return "redirect:/books/" + bookId;
        }

        Book book = bookService.findAllBooks().get(bookId);
        Review review = new Review();
        review.setBook(book);
        review.setUser(user);
        review.setRating(rating);
        review.setComment(comment);
        review.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        reviewService.saveReview(review);
        return "redirect:/books/" + bookId;
    }
    @PostMapping("/reviews/{id}/delete")
    public String deleteReview(@PathVariable Integer id, Principal principal, RedirectAttributes redirectAttributes) {
        Review review = reviewService.findAllReviews().get(id);
        if (!review.getUser().getUsername().equals(principal.getName())) {
            redirectAttributes.addFlashAttribute("error", "Вы не можете удалить чужой отзыв.");
            return "redirect:/books/" + review.getBook().getId();
        }
        reviewService.deleteReview(id);
        return "redirect:/books/" + review.getBook().getId();
    }

}
