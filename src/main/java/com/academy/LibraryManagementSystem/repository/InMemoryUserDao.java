package com.academy.LibraryManagementSystem.repository;

import com.academy.LibraryManagementSystem.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Repository
public class InMemoryUserDao {

    private final List<User> USER = new ArrayList<>();


    public List<User> findAll() {

        return USER;
    }

    public User saveUser(User user) {
        USER.add(user);
        return null;
    }

    public User findByEmail(String email) {
        return USER.stream().filter(element -> element.getEmail().equals(email)).findFirst().orElse(null);
    }

    public User updateUser(User user) {
        var userIndex = IntStream.range(0, USER.size())
                .filter(index -> USER.get(index).getEmail().equals(user.getEmail()))
                .findFirst().orElse(-1);
        if (userIndex > -1) {
            USER.set(userIndex, user);
            return user;
        }
        return null;
    }

    public void deleteUserByEmail(String email) {
        var user = findByEmail(email);
        if (user != null) {
            USER.remove(user);
        }
    }
}
