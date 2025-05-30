package com.academy.LibraryManagementSystem.repository;

import com.academy.LibraryManagementSystem.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    Optional<Book> findById(Integer id);

    void deleteById(Integer id);

    List<Book> findByTitleContainingIgnoreCase(String query);
    List<Book> findByGenreIgnoreCase(String genre);
    List<Book> findByTitleContainingIgnoreCaseAndGenreIgnoreCase(String title, String genre);

    @Query("SELECT DISTINCT b.genre FROM Book b WHERE b.genre IS NOT NULL")
    List<String> findDistinctGenres();
}
