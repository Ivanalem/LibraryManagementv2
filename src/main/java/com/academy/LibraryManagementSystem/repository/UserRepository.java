package com.academy.LibraryManagementSystem.repository;

import com.academy.LibraryManagementSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findAllUsers();
    void deleteUser(String email);

    User findByEmail(String email);



}
