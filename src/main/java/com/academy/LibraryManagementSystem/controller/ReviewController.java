package com.academy.LibraryManagementSystem.controller;

import com.academy.LibraryManagementSystem.model.Review;
import com.academy.LibraryManagementSystem.service.ReviewService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<Review> findAllReviews() {
        return reviewService.findAllReviews();
    }

    @PostMapping("save_review")
    public Review saveReview(@RequestBody Review review) {
        return reviewService.saveReview(review);
    }

    @GetMapping(value = "/{rating}")
    public Review findByRating(@PathVariable Integer rating) {
        return reviewService.findByRating(rating);
    }

    @PutMapping("update_review")
    public Review updateReview(@RequestBody Review review) {
        return reviewService.saveReview(review);
    }

    @Transactional
    @DeleteMapping("delete_comment/{comment}")
    public void deleteByComment(@PathVariable String comment) {
        reviewService.deleteByComment(comment);
    }

}
