package com.academy.LibraryManagementSystem.service.impl;

import com.academy.LibraryManagementSystem.model.Review;
import com.academy.LibraryManagementSystem.repository.ReviewRepository;
import com.academy.LibraryManagementSystem.service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> findAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public Review findByRating(Integer rating) {
        return reviewRepository.findByRating(rating);
    }

    @Override
    public Review updateReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public void deleteByComment(String comment) {
        reviewRepository.deleteByComment(comment);
    }
}
