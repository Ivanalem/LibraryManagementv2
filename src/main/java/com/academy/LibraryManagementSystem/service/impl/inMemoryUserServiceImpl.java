package com.academy.LibraryManagementSystem.service.impl;

import com.academy.LibraryManagementSystem.model.User;
import com.academy.LibraryManagementSystem.repository.InMemoryUserDao;
import com.academy.LibraryManagementSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class inMemoryUserServiceImpl implements UserService {

    private final InMemoryUserDao repository;

    @Autowired
    public inMemoryUserServiceImpl(InMemoryUserDao repository) {
        this.repository = repository;
    }

    @Override
    public List<User> findAllUsers() {
        return repository.findAll();
    }

    @Override
    public User saveUser(User user) {
        return repository.saveUser(user);
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public User updateUser(User user) {
        return repository.updateUser(user);
    }

    @Override
    public void deleteByEmail(String email) {

        repository.deleteUserByEmail(email);
    }


}
