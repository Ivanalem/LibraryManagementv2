package com.academy.LibraryManagementSystem.repository;

import com.academy.LibraryManagementSystem.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    Review findByRating(Integer rating);

    void deleteByComment(String comment);
}
