package com.academy.LibraryManagementSystem.service;

import com.academy.LibraryManagementSystem.model.Author;
import com.academy.LibraryManagementSystem.model.Book;
import com.academy.LibraryManagementSystem.repository.AuthorRepository;
import com.academy.LibraryManagementSystem.repository.BookRepository;
import com.academy.LibraryManagementSystem.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book testBook1;
    private Book testBook2;
    private Author testAuthor1;
    private Author testAuthor2;

    @BeforeEach
    void setUp() {
        testAuthor1 = new Author();
        testAuthor1.setId(1);
        testAuthor1.setName("Author One");

        testAuthor2 = new Author();
        testAuthor2.setId(2);
        testAuthor2.setName("Author Two");

        testBook1 = new Book();
        testBook1.setId(1);
        testBook1.setTitle("Book One");
        testBook1.setGenre("Fiction");
        testBook1.setAuthors(Arrays.asList(testAuthor1));

        testBook2 = new Book();
        testBook2.setId(2);
        testBook2.setTitle("Book Two");
        testBook2.setGenre("Science");
        testBook2.setAuthors(Arrays.asList(testAuthor2));
    }

    @Test
    void searchByTitleOrAuthor_ShouldReturnMatchingBooks() {
        // Arrange
        String query = "Book";
        when(bookRepository.findByTitleContainingIgnoreCaseOrAuthors_NameContainingIgnoreCase(query, query))
                .thenReturn(Arrays.asList(testBook1, testBook2));

        // Act
        List<Book> result = bookService.searchByTitleOrAuthor(query);

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(testBook1));
        assertTrue(result.contains(testBook2));
        verify(bookRepository).findByTitleContainingIgnoreCaseOrAuthors_NameContainingIgnoreCase(query, query);
    }

    @Test
    void findAllGenres_ShouldReturnAllGenres() {
        // Arrange
        List<String> genres = Arrays.asList("Fiction", "Science", "History");
        when(bookRepository.findAllGenres()).thenReturn(genres);

        // Act
        List<String> result = bookService.findAllGenres();

        // Assert
        assertEquals(3, result.size());
        assertTrue(result.contains("Fiction"));
        verify(bookRepository).findAllGenres();
    }

    @Test
    void findAllBooks_ShouldReturnAllBooks() {
        // Arrange
        when(bookRepository.findAll()).thenReturn(Arrays.asList(testBook1, testBook2));

        // Act
        List<Book> result = bookService.findAllBooks();

        // Assert
        assertEquals(2, result.size());
        verify(bookRepository).findAll();
    }

    @Test
    void saveBook_ShouldSaveAndReturnBook() {
        // Arrange
        when(bookRepository.save(testBook1)).thenReturn(testBook1);

        // Act
        Book result = bookService.saveBook(testBook1);

        // Assert
        assertEquals(testBook1, result);
        verify(bookRepository).save(testBook1);
    }

    @Test
    void findById_ShouldReturnBookWhenExists() {
        // Arrange
        when(bookRepository.findById(1)).thenReturn(Optional.of(testBook1));

        // Act
        Optional<Book> result = bookService.findById(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testBook1, result.get());
        verify(bookRepository).findById(1);
    }

    @Test
    void findById_ShouldReturnEmptyWhenNotExists() {
        // Arrange
        when(bookRepository.findById(99)).thenReturn(Optional.empty());

        // Act
        Optional<Book> result = bookService.findById(99);

        // Assert
        assertTrue(result.isEmpty());
        verify(bookRepository).findById(99);
    }

    @Test
    void updateBook_ShouldUpdateAndReturnBook() {
        // Arrange
        Book updatedBook = new Book();
        updatedBook.setId(1);
        updatedBook.setTitle("Updated Title");

        when(bookRepository.save(updatedBook)).thenReturn(updatedBook);

        // Act
        Book result = bookService.updateBook(updatedBook);

        // Assert
        assertEquals(updatedBook, result);
        verify(bookRepository).save(updatedBook);
    }

    @Test
    void deleteById_ShouldCallRepository() {
        // Arrange
        doNothing().when(bookRepository).deleteById(1);

        // Act
        bookService.deleteById(1);

        // Assert
        verify(bookRepository).deleteById(1);
    }

    @Test
    void saveBookWithAuthors_ShouldSaveAuthorsAndBook() {
        // Arrange
        List<Author> authors = Arrays.asList(testAuthor1, testAuthor2);
        when(authorRepository.saveAll(authors)).thenReturn(authors);
        when(bookRepository.save(testBook1)).thenReturn(testBook1);

        // Act
        bookService.saveBookWithAuthors(testBook1, authors);

        // Assert
        verify(authorRepository).saveAll(authors);
        verify(bookRepository).save(testBook1);
    }

    @Test
    void findByGenre_ShouldReturnBooksByGenre() {
        // Arrange
        String genre = "Fiction";
        when(bookRepository.findByGenre(genre)).thenReturn(Arrays.asList(testBook1));

        // Act
        List<Book> result = bookService.findByGenre(genre);

        // Assert
        assertEquals(1, result.size());
        assertEquals(testBook1, result.get(0));
        verify(bookRepository).findByGenre(genre);
    }

    @Test
    void findBookById_ShouldReturnBook() {
        // Arrange
        when(bookRepository.findBookById(1)).thenReturn(testBook1);

        // Act
        Book result = bookService.findBookById(1);

        // Assert
        assertEquals(testBook1, result);
        verify(bookRepository).findBookById(1);
    }

    @Test
    void saveBook_WhenBookIsNull_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            bookService.saveBook(null);
        });

        verifyNoInteractions(bookRepository);
    }

    @Test
    void updateBook_WhenBookIsNull_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            bookService.updateBook(null);
        });

        verifyNoInteractions(bookRepository);
    }

    @Test
    void saveBookWithAuthors_WhenAuthorsListIsNull_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            bookService.saveBookWithAuthors(testBook1, null);
        });

        verifyNoInteractions(authorRepository, bookRepository);
    }
}