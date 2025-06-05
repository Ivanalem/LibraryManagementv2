package com.academy.LibraryManagementSystem.controller;

import com.academy.LibraryManagementSystem.model.User;
import com.academy.LibraryManagementSystem.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Model model;

    @InjectMocks
    private AuthenticationController authenticationController;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setPassword("password");
        testUser.setEmail("test@example.com");
        testUser.setRole(Set.of(User.Role.ROLE_USER));
    }

    @Test
    void login_GetRequest_ShouldReturnLoginPage() {
        String result = authenticationController.login();
        assertEquals("login", result);
    }

    @Test
    void formLogin_WithValidCredentials_ShouldRedirectBasedOnRole() {
        // Arrange
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken("testuser", "password");

        Collection<? extends GrantedAuthority> authorities =
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        Authentication authentication = mock(Authentication.class);
        when(authentication.getAuthorities()).thenAnswer(i -> authorities);
        when(authenticationManager.authenticate(authToken)).thenReturn(authentication);

        // Act
        String result = authenticationController.formLogin("testuser", "password", model);

        // Assert
        assertEquals("redirect:index", result);
        verify(authenticationManager).authenticate(authToken);
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void formLogin_WithAdminRole_ShouldRedirectToAdminPage() {
        // Arrange
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken("admin", "adminpass");

        Collection<? extends GrantedAuthority> authorities =
                Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"));

        Authentication authentication = mock(Authentication.class);
        when(authentication.getAuthorities()).thenAnswer(i -> authorities);
        when(authenticationManager.authenticate(authToken)).thenReturn(authentication);

        // Act
        String result = authenticationController.formLogin("admin", "adminpass", model);

        // Assert
        assertEquals("redirect:admin-users", result);
    }

    @Test
    void formLogin_WithInvalidCredentials_ShouldReturnLoginWithError() {
        // Arrange
        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        // Act
        String result = authenticationController.formLogin("wronguser", "wrongpass", model);

        // Assert
        assertEquals("login", result);
        verify(model).addAttribute("error", "Неверное имя пользователя или пароль");
    }

    @Test
    void registerForm_ShouldReturnRegisterPageWithUserModel() {
        // Act
        String result = authenticationController.registerForm(model);

        // Assert
        assertEquals("register", result);
        verify(model).addAttribute(eq("user"), any(User.class));
    }

    @Test
    void registerSubmit_WithValidUser_ShouldRedirectToLogin() {
        // Arrange
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        // Act
        String result = authenticationController.registerSubmit(testUser, model);

        // Assert
        assertEquals("redirect:/login", result);
        verify(passwordEncoder).encode("password");
        verify(userRepository).save(testUser);
        assertEquals("encodedPassword", testUser.getPassword());
        assertEquals(Set.of(User.Role.ROLE_USER), testUser.getRole());
    }

    @Test
    void registerSubmit_WithDuplicateEmail_ShouldHandleException() {
        // Arrange
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(testUser))
                .thenThrow(new DataIntegrityViolationException("Duplicate email"));

        // Act
        String result = authenticationController.registerSubmit(testUser, model);

        // Assert
        assertEquals("redirect:/login", result);
        verify(model).addAttribute("error", "Пользователь с таким email уже зарегистрирован.");
    }

    @Test
    void registerSubmit_WithNullUser_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            authenticationController.registerSubmit(null, model);
        });
    }
}