package com.academy.LibraryManagementSystem.controller;

import com.academy.LibraryManagementSystem.model.User;
import com.academy.LibraryManagementSystem.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User testUser;
    private List<User> testUsers;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1);
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
        testUser.setPassword("password");

        testUsers = Arrays.asList(testUser);
    }

    @Test
    void findAllUser_ShouldReturnAllUsers() {
        when(userService.findAllUsers()).thenReturn(testUsers);

        List<User> result = userController.findAllUser();

        assertEquals(testUsers, result);
        verify(userService).findAllUsers();
    }

    @Test
    void saveUser_ShouldSaveUserAndReturnSuccessMessage() {
        doNothing().when(userService).saveUser(testUser);

        String result = userController.saveUser(testUser);

        assertEquals("User successfully saved", result);
        verify(userService).saveUser(testUser);
    }

    @Test
    void findByEmail_ShouldReturnUserWithGivenEmail() {
        when(userService.findByEmail("test@example.com")).thenReturn(testUser);

        User result = userController.findByEmail("test@example.com");

        assertEquals(testUser, result);
        verify(userService).findByEmail("test@example.com");
    }

    @Test
    void updateUser_ShouldUpdateAndReturnUser() {
        when(userService.updateUser(testUser)).thenReturn(testUser);

        User result = userController.updateUser(testUser);

        assertEquals(testUser, result);
        verify(userService).updateUser(testUser);
    }

    @Test
    void deleteUser_ShouldCallServiceToDeleteUser() {
        doNothing().when(userService).deleteByEmail("test@example.com");

        userController.deleteUser("test@example.com");

        verify(userService).deleteByEmail("test@example.com");
    }

    @Test
    void findByEmail_WhenUserNotFound_ShouldReturnNull() {
        when(userService.findByEmail("nonexistent@example.com")).thenReturn(null);

        User result = userController.findByEmail("nonexistent@example.com");

        assertNull(result);
        verify(userService).findByEmail("nonexistent@example.com");
    }

    @Test
    void updateUser_WhenUserIsNull_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            userController.updateUser(null);
        });

        verifyNoInteractions(userService);
    }
}