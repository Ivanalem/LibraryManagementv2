package com.academy.LibraryManagementSystem.service.impl;

import com.academy.LibraryManagementSystem.model.Review;
import com.academy.LibraryManagementSystem.repository.ReviewRepository;
import com.academy.LibraryManagementSystem.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
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
    public List<Review> findByBookId(Integer bookId) {

        return reviewRepository.findByBookId(bookId);
    }

    @Override
    public Review updateReview(Review review) {

        return reviewRepository.save(review);
    }

    @Override
    public void deleteReview(Integer reviewId) {

        reviewRepository.deleteById(reviewId);
    }


    public Optional<Review> getUserReviewForBook(Integer bookId, Integer userId) {
        return reviewRepository.findByBookIdAndUserId(bookId, userId);
    }

    @Override
    public Optional<Review> findReviewById(Integer id) {
        return reviewRepository.findById(id);
    }

}
