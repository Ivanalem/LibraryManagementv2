package com.academy.LibraryManagementSystem.service;

import com.academy.LibraryManagementSystem.model.User;
import com.academy.LibraryManagementSystem.repository.UserRepository;
import com.academy.LibraryManagementSystem.service.impl.UserServiceImpl;
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
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser1;
    private User testUser2;
    private List<User> userList;

    @BeforeEach
    void setUp() {
        testUser1 = new User();
        testUser1.setId(1);
        testUser1.setUsername("user1");
        testUser1.setEmail("user1@example.com");

        testUser2 = new User();
        testUser2.setId(2);
        testUser2.setUsername("user2");
        testUser2.setEmail("user2@example.com");

        userList = Arrays.asList(testUser1, testUser2);
    }

    @Test
    void findAllUsers_ShouldReturnAllUsers() {
        // Arrange
        when(userRepository.findAll()).thenReturn(userList);

        // Act
        List<User> result = userService.findAllUsers();

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(testUser1));
        assertTrue(result.contains(testUser2));
        verify(userRepository).findAll();
    }

    @Test
    void findAllUsers_WhenNoUsers_ShouldReturnEmptyList() {
        // Arrange
        when(userRepository.findAll()).thenReturn(List.of());

        // Act
        List<User> result = userService.findAllUsers();

        // Assert
        assertTrue(result.isEmpty());
        verify(userRepository).findAll();
    }

    @Test
    void saveUser_ShouldSaveAndReturnUser() {
        // Arrange
        when(userRepository.save(testUser1)).thenReturn(testUser1);

        // Act
        User result = userService.saveUser(testUser1);

        // Assert
        assertNotNull(result);
        assertEquals(testUser1, result);
        verify(userRepository).save(testUser1);
    }

    @Test
    void findByEmail_ShouldReturnUser() {
        // Arrange
        when(userRepository.findByEmail("user1@example.com")).thenReturn(testUser1);

        // Act
        User result = userService.findByEmail("user1@example.com");

        // Assert
        assertEquals(testUser1, result);
        verify(userRepository).findByEmail("user1@example.com");
    }

    @Test
    void findByEmail_WhenUserNotFound_ShouldReturnNull() {
        // Arrange
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(null);

        // Act
        User result = userService.findByEmail("nonexistent@example.com");

        // Assert
        assertNull(result);
        verify(userRepository).findByEmail("nonexistent@example.com");
    }

    @Test
    void updateUser_ShouldUpdateAndReturnUser() {
        // Arrange
        User updatedUser = new User();
        updatedUser.setId(1);
        updatedUser.setUsername("updatedUser");
        updatedUser.setEmail("updated@example.com");

        when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        // Act
        User result = userService.updateUser(updatedUser);

        // Assert
        assertEquals(updatedUser, result);
        verify(userRepository).save(updatedUser);
    }

    @Test
    void deleteByEmail_ShouldCallRepository() {
        // Arrange
        doNothing().when(userRepository).deleteByEmail("user1@example.com");

        // Act
        userService.deleteByEmail("user1@example.com");

        // Assert
        verify(userRepository).deleteByEmail("user1@example.com");
    }

    @Test
    void findByUsername_ShouldReturnOptionalUser() {
        // Arrange
        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(testUser1));

        // Act
        Optional<User> result = userService.findByUsername("user1");

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testUser1, result.get());
        verify(userRepository).findByUsername("user1");
    }

    @Test
    void findByUsername_WhenUserNotFound_ShouldReturnEmptyOptional() {
        // Arrange
        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userService.findByUsername("nonexistent");

        // Assert
        assertTrue(result.isEmpty());
        verify(userRepository).findByUsername("nonexistent");
    }

    @Test
    void saveUser_WhenUserIsNull_ShouldThrowException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            userService.saveUser(null);
        });

        verifyNoInteractions(userRepository);
    }

    @Test
    void updateUser_WhenUserIsNull_ShouldThrowException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUser(null);
        });

        verifyNoInteractions(userRepository);
    }
}