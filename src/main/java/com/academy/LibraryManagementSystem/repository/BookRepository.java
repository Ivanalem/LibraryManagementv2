package com.academy.LibraryManagementSystem.repository;

import com.academy.LibraryManagementSystem.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    Book findByTitle(String title);

    void deleteById(Integer id);
}
