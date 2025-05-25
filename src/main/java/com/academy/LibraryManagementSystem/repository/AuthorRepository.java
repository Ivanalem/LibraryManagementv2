package com.academy.LibraryManagementSystem.repository;

import com.academy.LibraryManagementSystem.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Author findByName(String name);

    void deleteByName(String name);

}
