package com.academy.LibraryManagementSystem.repository;

import com.academy.LibraryManagementSystem.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    Review findByRating(Integer rating);

    void deleteById(Integer reviewId);

    Optional<Review> findByBookIdAndUserId(Integer bookId, Integer userId);

    List<Review> findByBookId(Integer bookId);

}
