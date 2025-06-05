package com.academy.LibraryManagementSystem;

import com.academy.LibraryManagementSystem.repository.UserRepository;
import com.academy.LibraryManagementSystem.service.AuthorService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration
public class MockServiceTestConfig {

    @Bean
    public AuthorService authorService() {
        return Mockito.mock(AuthorService.class);
    }

    @Bean
    public UserRepository userRepository() {
        return Mockito.mock(UserRepository.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return Mockito.mock(PasswordEncoder.class);
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return Mockito.mock(AuthenticationManager.class);
    }
}