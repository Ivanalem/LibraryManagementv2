package com.academy.LibraryManagementSystem.service;

import com.academy.LibraryManagementSystem.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    List<User> findAllUsers();

    User saveUser(User user);

    User findByEmail(String email);

    User updateUser(User user);

    void deleteByEmail(String email);


    Optional<User> findByUsername(String username);
}
