package com.academy.LibraryManagementSystem.service;

import com.academy.LibraryManagementSystem.model.Review;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ReviewService {

    List<Review> findAllReviews();

    Review saveReview(Review review);

    List<Review> findByBookId(Integer bookId);

    Review updateReview(Review review);

    void deleteReview(Integer reviewId);

    Optional<Review> getUserReviewForBook(Integer bookId, Integer userId);


}
