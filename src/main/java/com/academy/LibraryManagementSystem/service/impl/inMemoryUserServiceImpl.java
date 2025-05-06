package com.academy.LibraryManagementSystem.service.impl;

import lombok.AllArgsConstructor;
import com.academy.LibraryManagementSystem.model.User;
import com.academy.LibraryManagementSystem.repository.InMemoryStudentDao;
import com.academy.LibraryManagementSystem.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class inMemoryUserServiceImpl implements UserService {
    private final InMemoryStudentDao repository;

    @Override
    public List<User> findAllUsers() {
        return repository.findAllUsers();
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
    public void deleteUser(String email) {
        repository.deleteUser(email);
    }

}
