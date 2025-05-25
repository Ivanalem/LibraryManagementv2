package com.academy.LibraryManagementSystem.service;

import com.academy.LibraryManagementSystem.model.Review;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {

    List<Review> findAllReviews();

    Review saveReview(Review review);

    Review findByRating(Integer rating);

    Review updateReview(Review review);

    void deleteByComment(String comment);
}
